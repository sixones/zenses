/*
 * Darwin/MacOS X Support
 *
 * (c) 2002-2004 Nathan Hjelm <hjelmn@users.sourceforge.net>
 *
 * 0.1.8 (01/12/2004):
 *   - Fixed several memory leaks.
 *   - Readded 10.0 support
 *   - Added support for USB fuctions defined in 10.3 and above
 * (01/02/2003):
 *   - Applied a patch by Philip Edelbrock <phil@edgedesign.us> that fixes a bug in usb_control_msg.
 * (12/16/2003):
 *   - Even better error printing.
 *   - Devices that cannot be opened can have their interfaces opened.
 * 0.1.6 (05/12/2002):
 *   - Fixed problem where libusb holds resources after program completion.
 *   - Mouse should no longer freeze up now.
 * 0.1.2 (02/13/2002):
 *   - Bulk functions should work properly now.
 * 0.1.1 (02/11/2002):
 *   - Fixed major bug (device and interface need to be released after use)
 * 0.1.0 (01/06/2002):
 *   - Tested driver with gphoto (works great as long as Image Capture isn't running)
 * 0.1d  (01/04/2002):
 *   - Implimented clear_halt and resetep
 *   - Uploaded to CVS.
 * 0.1b  (01/04/2002):
 *   - Added usb_debug line to bulk read and write function.
 * 0.1a  (01/03/2002):
 *   - Driver mostly completed using the macosx driver I wrote for my rioutil software.
 *
 * this driver is EXPERIMENTAL, use at your own risk and e-mail me bug reports.
 *
 * Derived from Linux version by Richard Tobin.
 * Also partly derived from BSD version.
 *
 * This library is covered by the LGPL, read LICENSE for details.
 */

#ifdef HAVE_CONFIG_H
#include "config.h"
#endif

#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>

/* standard includes for darwin/os10 (IOKit) */
#include <mach/mach_port.h>
#include <IOKit/IOCFBundle.h>
#include <IOKit/usb/IOUSBLib.h>
#include <IOKit/IOCFPlugIn.h>

#include "usbi.h"

/* some defines */
/* IOUSBInterfaceInferface */
/* 10.4 defines copied over from CVS by RAL */
/* since we need to work for OS X 10.3.9 we will force to use
 * the 10.3 versions */

/*#if defined (kIOUSBInterfaceInterfaceID220)

#warning "libusb being compiled for 10.4 or later"
#define usb_interface_t IOUSBInterfaceInterface220
#define InterfaceInterfaceID kIOUSBInterfaceInterfaceID220
#define InterfaceVersion 220

#elif defined (kIOUSBInterfaceInterfaceID197)*/

#warning "libusb being compiled for 10.3 or higher"
#define usb_interface_t IOUSBInterfaceInterface197
#define InterfaceInterfaceID kIOUSBInterfaceInterfaceID197
#define InterfaceVersion 197

/*#elif defined (IOUSBINTERFACE_FUNCS_190)

#warning "libusb being compiled for 10.2 or higher"
#define usb_interface_t IOUSBInterfaceInterface190
#define InterfaceInterfaceID kIOUSBInterfaceInterfaceID190
#define InterfaceVersion 190

#elif defined (IOUSBINTERFACE_FUNCS_182)

#warning "libusb being compiled for 10.1 or higher"
#define usb_interface_t IOUSBInterfaceInterface182
#define InterfaceInterfaceID kIOUSBInterfaceInterfaceID182
#define InterfaceVersion 182

#else

/ * No timeout functions available! Time to upgrade your os. * /
#warning "libusb being compiled without support for timout bulk functions! 10.0 and up"
#define usb_interface_t IOUSBInterfaceInterface
#define InterfaceInterfaceID kIOUSBInterfaceInterfaceID
#define LIBUSB_NO_TIMEOUT_INTERFACE
#define InterfaceVersion 180

#endif */

/* IOUSBDeviceInterface */
#if defined (IOUSBDEVICE_FUNCS_197)

#define usb_device_t    IOUSBDeviceInterface197
#define DeviceInterfaceID kIOUSBDeviceInterfaceID197
#define DeviceVersion 197

#elif defined (IOUSBDEVICE_FUNCS_187)

#define usb_device_t    IOUSBDeviceInterface187
#define DeviceInterfaceID kIOUSBDeviceInterfaceID187
#define DeviceVersion 187

#elif defined (IOUSBDEVICE_FUNCS_182)

#define usb_device_t    IOUSBDeviceInterface182
#define DeviceInterfaceID kIOUSBDeviceInterfaceID182
#define DeviceVersion 182

#else

#define usb_device_t    IOUSBDeviceInterface
#define DeviceInterfaceID kIOUSBDeviceInterfaceID
#define LIBUSB_NO_TIMEOUT_DEVICE
#define LIBUSB_NO_SEIZE_DEVICE
#define DeviceVersion 180

#endif

typedef IOReturn io_return_t;
typedef IOCFPlugInInterface *io_cf_plugin_ref_t;
typedef SInt32 s_int32_t;

/* Darwin/OS X impl does not use fd field, instead it uses this */
struct darwin_dev_handle {
  usb_device_t **device;
  usb_interface_t **interface;
  int open;
};

static CFMutableDictionaryRef matchingDict;
static IONotificationPortRef gNotifyPort;
static mach_port_t masterPort = MACH_PORT_NULL;

static void darwin_cleanup (void)
{
  IONotificationPortDestroy(gNotifyPort);
  mach_port_deallocate(mach_task_self(), masterPort);
}

static char *darwin_error_str (int result) {
  switch (result) {
  case kIOReturnSuccess:
    return "no error";
  case kIOReturnNotOpen:
    return "device not opened for exclusive access";
  case kIOReturnNoDevice:
    return "no connection to an IOService";
  case kIOUSBNoAsyncPortErr:
    return "no asyc port has been opened for interface";
  case kIOReturnExclusiveAccess:
    return "another process has device opened for exclusive access";
  case kIOUSBPipeStalled:
    return "pipe is stalled";
  case kIOReturnError:
    return "could not establish a connection to Darin kernel";
  case kIOReturnBadArgument:
    return "invalid argument";
  default:
    return "unknown error";
  }
}

/* not a valid errorno outside darwin.c */
#define LUSBDARWINSTALL (ELAST+1)

static int darwin_to_errno (int result) {
  switch (result) {
  case kIOReturnSuccess:
    return 0;
  case kIOReturnNotOpen:
    return EBADF;
  case kIOReturnNoDevice:
  case kIOUSBNoAsyncPortErr:
    return ENXIO;
  case kIOReturnExclusiveAccess:
    return EBUSY;
  case kIOUSBPipeStalled:
    return LUSBDARWINSTALL;
  case kIOReturnBadArgument:
    return EINVAL;
  case kIOReturnError:
  default:
    return 1;
  }
}

int usb_os_open(usb_dev_handle *dev)
{
  struct darwin_dev_handle *device;

  io_return_t result;
  io_iterator_t deviceIterator;
  io_service_t usbDevice;
  io_cf_plugin_ref_t *plugInInterface = NULL;

  usb_device_t **darwin_device;

  s_int32_t score;
  u_int32_t location = *((u_int32_t *)dev->device->dev);
  u_int32_t dlocation;

  if (!dev)
    USB_ERROR(-ENXIO);

  if (!masterPort)
    USB_ERROR(-EINVAL);

  device = calloc(1, sizeof(struct darwin_dev_handle));
  if (!device)
    USB_ERROR(-ENOMEM);

  if (usb_debug > 3)
    fprintf(stderr, "usb_os_open: %04x:%04x\n",
	    dev->device->descriptor.idVendor,
	    dev->device->descriptor.idProduct);

  /* set up the matching dictionary for class IOUSBDevice and its subclasses.
     It will be consumed by the IOServiceGetMatchingServices call */
  if ((matchingDict = IOServiceMatching(kIOUSBDeviceClassName)) == NULL) {
    darwin_cleanup ();
    
    USB_ERROR(-ENOMEM);
  }

  result = IOServiceGetMatchingServices(masterPort,
					matchingDict,
					&deviceIterator);
  matchingDict = NULL;

  if (result != kIOReturnSuccess)
    USB_ERROR_STR(-darwin_to_errno (result), "usb_os_open(IOServiceGetMatchingServices): %s", darwin_error_str(result));

  while (IOIteratorIsValid (deviceIterator) && (usbDevice = IOIteratorNext(deviceIterator))) {
    /* Create an intermediate plug-in */
    result = IOCreatePlugInInterfaceForService(usbDevice, kIOUSBDeviceUserClientTypeID,
                                               kIOCFPlugInInterfaceID, &plugInInterface,
                                               &score);

    result = IOObjectRelease(usbDevice);
    if (result || !plugInInterface)
      continue;

    (*plugInInterface)->QueryInterface(plugInInterface, CFUUIDGetUUIDBytes(DeviceInterfaceID),
                                       (LPVOID)&darwin_device);

    (*plugInInterface)->Stop(plugInInterface);
    IODestroyPlugInInterface (plugInInterface);
    plugInInterface = NULL;

    if (!darwin_device)
      continue;

    result = (*(darwin_device))->GetLocationID(darwin_device, (u_int32_t *)&dlocation);
    if (dlocation == location) {
      device->device = darwin_device;
      break;
    }

    (*darwin_device)->Release(darwin_device);
  }

  IOObjectRelease(deviceIterator);

  if (device->device == NULL)
    USB_ERROR_STR (-ENOENT, "usb_os_open: %s\n", "Device not found!");

#if !defined (LIBUSB_NO_SEIZE_DEVICE)
  result = (*(device->device))->USBDeviceOpenSeize (device->device);
#else
  /* No Seize in OS X 10.0 (Darwin 1.4) */
  result = (*(device->device))->USBDeviceOpen (device->device);
#endif

  if (result != kIOReturnSuccess) {
    switch (result) {
    case kIOReturnExclusiveAccess:
      if (usb_debug > 0)
	fprintf (stderr, "usb_os_open(USBDeviceOpenSeize): %s\n", darwin_error_str(result));
      break;
    default:
      (*(device->device))->Release (device->device);
      USB_ERROR_STR(-darwin_to_errno (result), "usb_os_open(USBDeviceOpenSeize): %s", darwin_error_str(result));
    }
    
    device->open = 0;
  } else
    device->open = 1;
    
  dev->impl_info = device;
  dev->interface = -1;
  dev->altsetting = -1;

  return 0;
}

int usb_os_close(usb_dev_handle *dev)
{
  struct darwin_dev_handle *device;
  io_return_t result;

  if (!dev)
    USB_ERROR(-ENXIO);

  if ((device = dev->impl_info) == NULL)
    USB_ERROR(-ENOENT);

  usb_release_interface(dev, dev->interface);

  if (usb_debug > 3)
    fprintf(stderr, "usb_os_close: %04x:%04x\n",
	    dev->device->descriptor.idVendor,
	    dev->device->descriptor.idProduct);

  if (device->open == 1)
    result = (*(device->device))->USBDeviceClose(device->device);
  else
    result = kIOReturnSuccess;

  /* device may not need to be released, but if it has to... */
  (*(device->device))->Release(device->device);

  if (result != kIOReturnSuccess)
    USB_ERROR_STR(-darwin_to_errno(result), "usb_os_close(USBDeviceClose): %s", darwin_error_str(result));

  free (device);

  return 0;
}

static int claim_interface ( usb_dev_handle *dev, int interface )
{
  io_iterator_t interface_iterator;
  io_service_t  usbInterface;
  io_return_t result;
  io_cf_plugin_ref_t *plugInInterface = NULL;

  IOUSBFindInterfaceRequest request;

  struct darwin_dev_handle *device;
  long score;
  int current_interface;

  device = dev->impl_info;

  request.bInterfaceClass = kIOUSBFindInterfaceDontCare;
  request.bInterfaceSubClass = kIOUSBFindInterfaceDontCare;
  request.bInterfaceProtocol = kIOUSBFindInterfaceDontCare;
  request.bAlternateSetting = kIOUSBFindInterfaceDontCare;

  result = (*(device->device))->CreateInterfaceIterator(device->device, &request, &interface_iterator);
  if (result != kIOReturnSuccess)
    USB_ERROR_STR (-darwin_to_errno(result), "claim_interface(CreateInterfaceIterator): %s",
		   darwin_error_str(result));

  for ( current_interface=0; current_interface<=interface; current_interface++ ) {
    usbInterface = IOIteratorNext(interface_iterator);
    if ( usb_debug > 3 )
      fprintf ( stderr, "Interface %d of device is 0x%x\n",
		current_interface, usbInterface );
  }
  current_interface--;

  /* the interface iterator is no longer needed, release it */
  IOObjectRelease(interface_iterator);

  if (!usbInterface) {
    u_int8_t nConfig;			     /* Index of configuration to use */
    IOUSBConfigurationDescriptorPtr configDesc; /* to describe which configuration to select */
    /* Only a composite class device with no vendor-specific driver will
       be configured. Otherwise, we need to do it ourselves, or there
       will be no interfaces for the device. */

    if ( usb_debug > 3 )
      fprintf ( stderr,"claim_interface: No interface found; selecting configuration\n" );

    result = (*(device->device))->GetNumberOfConfigurations ( device->device, &nConfig );
    if (result != kIOReturnSuccess)
      USB_ERROR_STR(-darwin_to_errno(result), "claim_interface(GetNumberOfConfigurations): %s",
		    darwin_error_str(result));
    
    if (nConfig < 1)
      USB_ERROR_STR(-ENXIO ,"claim_interface(GetNumberOfConfigurations): no configurations");
    else if ( nConfig > 1 && usb_debug > 0 )
      fprintf ( stderr, "claim_interface: device has more than one"
		" configuration, using the first (warning)\n" );

    if ( usb_debug > 3 )
      fprintf ( stderr, "claim_interface: device has %d configuration%s\n",
		(int)nConfig, (nConfig>1?"s":"") );

    /* Always use the first configuration */
    result = (*(device->device))->GetConfigurationDescriptorPtr ( (device->device), 0, &configDesc );
    if (result != kIOReturnSuccess) {
      if (device->open == 1) {
        (*(device->device))->USBDeviceClose ( (device->device) );
        (*(device->device))->Release ( (device->device) );
      }

      USB_ERROR_STR(-darwin_to_errno(result), "claim_interface(GetConfigurationDescriptorPtr): %s",
		    darwin_error_str(result));
    } else if ( usb_debug > 3 )
      fprintf ( stderr, "claim_interface: configuration value is %d\n",
		configDesc->bConfigurationValue );

    if (device->open == 1) {
      result = (*(device->device))->SetConfiguration ( (device->device), configDesc->bConfigurationValue );

      if (result != kIOReturnSuccess) {
	(*(device->device))->USBDeviceClose ( (device->device) );
	(*(device->device))->Release ( (device->device) );

	USB_ERROR_STR(-darwin_to_errno(result), "claim_interface(SetConfiguration): %s",
		      darwin_error_str(result));
      }
    }
    
    request.bInterfaceClass = kIOUSBFindInterfaceDontCare;
    request.bInterfaceSubClass = kIOUSBFindInterfaceDontCare;
    request.bInterfaceProtocol = kIOUSBFindInterfaceDontCare;
    request.bAlternateSetting = kIOUSBFindInterfaceDontCare;

    /* Now go back and get the chosen interface */
    result = (*(device->device))->CreateInterfaceIterator(device->device, &request, &interface_iterator);
    if (result != kIOReturnSuccess)
      USB_ERROR_STR (-darwin_to_errno(result), "claim_interface(CreateInterfaceIterator): %s",
		     darwin_error_str(result));

    for (current_interface = 0 ; current_interface <= interface ; current_interface++) {
      usbInterface = IOIteratorNext(interface_iterator);

      if ( usb_debug > 3 )
	fprintf ( stderr, "claim_interface: Interface %d of device is 0x%x\n",
		  current_interface, usbInterface );
    }
    current_interface--;

    /* the interface iterator is no longer needed, release it */
    IOObjectRelease(interface_iterator);

    if (!usbInterface)
      USB_ERROR_STR (-ENOENT, "claim_interface: interface iterator returned NULL");
  }

  result = IOCreatePlugInInterfaceForService(usbInterface,
					     kIOUSBInterfaceUserClientTypeID,
					     kIOCFPlugInInterfaceID,
					     &plugInInterface, &score);
  /* No longer need the usbInterface object after getting the plug-in */
  result = IOObjectRelease(usbInterface);
  if (result || !plugInInterface)
    USB_ERROR(-ENOENT);

  /* Now create the device interface for the interface */
  result = (*plugInInterface)->QueryInterface(plugInInterface,
					      CFUUIDGetUUIDBytes(InterfaceInterfaceID),
					      (LPVOID) &device->interface);

  /* No longer need the intermediate plug-in */
  (*plugInInterface)->Stop(plugInInterface);
  IODestroyPlugInInterface (plugInInterface);

  if (result != kIOReturnSuccess)
    USB_ERROR_STR(-darwin_to_errno(result), "claim_interface(QueryInterface): %s",
		  darwin_error_str(result));

  if (!device->interface)
    USB_ERROR(-EACCES);

  if ( usb_debug > 3 )
    fprintf ( stderr, "claim_interface: Interface %d of device from QueryInterface is 0x%x\n",
	      current_interface, device->interface);

  /* claim the interface */
  result = (*(device->interface))->USBInterfaceOpen(device->interface);
  if (result)
    USB_ERROR_STR(-darwin_to_errno(result), "claim_interface(USBInterfaceOpen): %s",
		  darwin_error_str(result));

  return 0;
}

int usb_set_configuration(usb_dev_handle *dev, int configuration)
{
  struct darwin_dev_handle *device;
  io_return_t result;

  if ( usb_debug > 3 )
    fprintf ( stderr, "usb_set_configuration: called for config %x\n", configuration );

  if (!dev)
    USB_ERROR_STR ( -ENXIO, "usb_set_configuration: called with null device\n" );

  if ((device = dev->impl_info) == NULL)
    USB_ERROR_STR ( -ENOENT, "usb_set_configuration: device not properly initialized" );

  /* Setting configuration will invalidate the interface, so we need
     to reclaim it. First, dispose of existing interface, if any. */
  if ( device->interface )
    usb_release_interface(dev, dev->interface);

  result = (*(device->device))->SetConfiguration(device->device, configuration);

  if (result)
    USB_ERROR_STR(-darwin_to_errno(result), "usb_set_configuration(SetConfiguration): %s",
		  darwin_error_str(result));

  /* Reclaim interface: assume zero */
  if (dev->interface != -1)
    result = claim_interface(dev, dev->interface);

  dev->config = configuration;

  return result;
}

int usb_claim_interface(usb_dev_handle *dev, int interface)
{
  struct darwin_dev_handle *device = dev->impl_info;

  io_return_t result;

  if ( usb_debug > 3 )
    fprintf ( stderr, "usb_claim_interface: called for interface %d\n", interface );

  if (!device)
    USB_ERROR_STR ( -ENOENT, "usb_claim_interface: device is NULL" );

  if (!(device->device))
    USB_ERROR_STR ( -EINVAL, "usb_claim_interface: device->device is NULL" );

  /* If we have already claimed an interface, release it */
  if ( device->interface )
    usb_release_interface(dev, dev->interface);

  result = claim_interface ( dev, interface );
  if ( result )
    USB_ERROR_STR ( result, "usb_claim_interface: couldn't claim interface" );

  dev->interface = interface;

  /* interface is claimed and async IO is set up: return 0 */
  return 0;
}

int usb_release_interface(usb_dev_handle *dev, int interface)
{
  struct darwin_dev_handle *device;
  io_return_t result;

  if (!dev)
    USB_ERROR(-ENXIO);

  if ((device = dev->impl_info) == NULL)
    USB_ERROR(-ENOENT);

  /* interface is not open */
  if (!device->interface)
    return 0;

  result = (*(device->interface))->USBInterfaceClose(device->interface);

  if (result != kIOReturnSuccess)
    USB_ERROR_STR(-darwin_to_errno(result), "usb_release_interface(USBInterfaceClose): %s",
		  darwin_error_str(result));

  result = (*(device->interface))->Release(device->interface);

  if (result != kIOReturnSuccess)
    USB_ERROR_STR(-darwin_to_errno(result), "usb_release_interface(Release): %s",
		  darwin_error_str(result));

  device->interface = NULL;

  dev->interface = -1;
  dev->altsetting = -1;

  return 0;
}

int usb_set_altinterface(usb_dev_handle *dev, int alternate)
{
  struct darwin_dev_handle *device;
  io_return_t result;

  if (!dev)
    USB_ERROR(-ENXIO);

  if ((device = dev->impl_info) == NULL)
    USB_ERROR(-ENOENT);

  /* interface is not open */
  if (!device->interface)
    USB_ERROR_STR(-EACCES, "usb_set_altinterface: interface used without being claimed");

  result = (*(device->interface))->SetAlternateInterface(device->interface, alternate);

  if (result)
    USB_ERROR_STR(result, "usb_set_altinterface: could not set alternate interface");

  dev->altsetting = alternate;

  return 0;
}

/* simple function that figures out what pipeRef is associated with an endpoint */
static int ep_to_pipeRef (struct darwin_dev_handle *device, int ep)
{
  io_return_t ret;

  u_int8_t numep, direction, number;
  u_int8_t dont_care1, dont_care3;
  u_int16_t dont_care2;

  int i;

  if (usb_debug > 1)
    fprintf(stderr, "Converting ep address to pipeRef.\n");

  /* retrieve the total number of endpoints on this interface */
  ret = (*(device->interface))->GetNumEndpoints(device->interface, &numep);
  if ( ret ) {
    if ( usb_debug > 1 )
      fprintf ( stderr, "ep_to_pipeRef: interface is %x\n", device->interface );
    USB_ERROR_STR ( -ret, "ep_to_pipeRef: can't get number of endpoints for interface" );
  }

  /* iterate through the pipeRefs until we find the correct one */
  for (i = 1 ; i <= numep ; i++) {
    ret = (*(device->interface))->GetPipeProperties(device->interface, i, &direction, &number,
						    &dont_care1, &dont_care2, &dont_care3);

    if (ret != kIOReturnSuccess) {
      fprintf (stderr, "ep_to_pipeRef: an error occurred getting pipe information on pipe %d\n",
	       i );
      USB_ERROR_STR(-darwin_to_errno(ret), "ep_to_pipeRef(GetPipeProperties): %s", darwin_error_str(ret));
    }

    if (usb_debug > 1)
      fprintf (stderr, "ep_to_pipeRef: Pipe %i: DIR: %i number: %i\n", i, direction, number);

    /* calculate the endpoint of the pipe and check it versus the requested endpoint */
    if ( ((direction << 7 & USB_ENDPOINT_DIR_MASK) | (number & USB_ENDPOINT_ADDRESS_MASK)) == ep ) {
      if (usb_debug > 1)
	fprintf(stderr, "ep_to_pipeRef: pipeRef for ep address 0x%02x found: 0x%02x\n", ep, i);

      return i;
    }
  }

  if (usb_debug > 1)
    fprintf(stderr, "ep_to_pipeRef: No pipeRef found with endpoint address 0x%02x.\n", ep);
  
  /* none of the found pipes match the requested endpoint */
  return -1;
}

static void rw_completed(void *ret_io_size, io_return_t result, void *io_size)
{
  if (usb_debug > 2)
    fprintf(stderr, "io async operation completed. result = %i, size = %i\n", result,
	    (u_int32_t)io_size);

  if (result == kIOReturnSuccess)
    *(u_int32_t *)ret_io_size = (u_int32_t)io_size;
  else
    *(u_int32_t *)ret_io_size = result;

  CFRunLoopStop(CFRunLoopGetCurrent());
}

int usb_bulk_write(usb_dev_handle *dev, int ep, char *bytes, int size,
                   int timeout)
{
  struct darwin_dev_handle *device;

  io_return_t result = -1;

  CFRunLoopSourceRef cfSource;
  int pipeRef;

  u_int8_t  direction, number, transferType, interval;
  u_int16_t maxPacketSize;
  u_int32_t written;

  if (!dev)
    USB_ERROR_STR ( -ENXIO, "usb_bulk_read: called with null device" );

  if ((device = dev->impl_info) == NULL)
    USB_ERROR_STR ( -ENOENT, "usb_bulk_read: device not initialized" );

  /* interface is not open */
  if (!device->interface)
    USB_ERROR_STR(-EACCES, "usb_bulk_write: interface used without being claimed");

  if ((pipeRef = ep_to_pipeRef(device, ep)) < 0)
    USB_ERROR(-EINVAL);

  if (usb_debug > 3)
    fprintf(stderr, "usb_bulk_write: endpoint=0x%02x size=%i TO=%i\n", ep, size, timeout);

  (*(device->interface))->GetPipeProperties ( device->interface, pipeRef, &direction, &number,
					     &transferType, &maxPacketSize, &interval );

  (*(device->interface))->CreateInterfaceAsyncEventSource(device->interface, &cfSource);
  CFRunLoopAddSource(CFRunLoopGetCurrent(), cfSource, kCFRunLoopDefaultMode);

  /* there seems to be no way to determine how many bytes are actually written */
#if !defined (LIBUSB_NO_TIMEOUT_INTERFACE)
  if ( transferType != kUSBInterrupt )
    result = (*(device->interface))->WritePipeAsyncTO(device->interface, pipeRef,
						      bytes, size, 0, timeout,
						      (IOAsyncCallback1)rw_completed,
						      (void *)&written);
  else
#endif
    result = (*(device->interface))->WritePipeAsync(device->interface, pipeRef, bytes,
						    size, (IOAsyncCallback1)rw_completed,
						    (void *)&written);
  
  if (result == kIOReturnSuccess)
    /* wait for write to complete */
    CFRunLoopRun();

  CFRunLoopRemoveSource(CFRunLoopGetCurrent(), cfSource, kCFRunLoopDefaultMode);

  if (result != kIOReturnSuccess)
    USB_ERROR_STR(-darwin_to_errno(result), "usb_bulk_write(WritePipeAsyncTO): %s", darwin_error_str(result));

  return written;
}

int usb_bulk_read(usb_dev_handle *dev, int ep, char *bytes, int size,
                  int timeout)
{
  struct darwin_dev_handle *device;
  io_return_t result = -1;

  int pipeRef;

  u_int32_t retrieved = 0;
  u_int16_t maxPacketSize;
  u_int8_t  direction, number, transferType, interval;

  CFRunLoopSourceRef cfSource;

  if (usb_debug)
    fprintf (stderr, "usb_bulk_read: ep addr = 0x%02x\n", ep);

  ep |= 0x80; /* USB_DIR_OUT */

  if (!dev)
    USB_ERROR_STR ( -ENXIO, "usb_bulk_read: called with null device" );

  if ((device = dev->impl_info) == NULL)
    USB_ERROR_STR ( -ENOENT, "usb_bulk_read: device not initialized" );

  /* interface is not open */
  if (!device->interface)
    USB_ERROR_STR(-EACCES, "usb_bulk_read: interface used without being claimed");

  if ((pipeRef = ep_to_pipeRef(device, ep)) == -1)
    USB_ERROR_STR ( -EINVAL, "usb_bulk_read: invalid pipe reference" );

  if (usb_debug > 3)
    fprintf(stderr, "usb_bulk_read: endpoint=0x%02x size=%i timeout=%fsec\n" , ep, size, (double)timeout/1000.0 );

  (*(device->interface))->GetPipeProperties ( device->interface, pipeRef, &direction, &number,
					     &transferType, &maxPacketSize, &interval );

  (*(device->interface))->CreateInterfaceAsyncEventSource(device->interface, &cfSource);
  CFRunLoopAddSource(CFRunLoopGetCurrent(), cfSource, kCFRunLoopDefaultMode);

#if !defined (LIBUSB_NO_TIMEOUT_INTERFACE)
  if ( transferType == kUSBInterrupt ) {
#endif
    /* This is an interrupt pipe. We can't specify a timeout. */
    if (usb_debug > 3)
      fprintf(stderr, "usb_bulk_read: interrupt pipe\n" );
    result = (*(device->interface))->ReadPipeAsync(device->interface, pipeRef, bytes,
					      size, (IOAsyncCallback1)rw_completed,
					      (void *)&retrieved);
#if !defined (LIBUSB_NO_TIMEOUT_INTERFACE)
  } else {
    result = (*(device->interface))->ReadPipeAsyncTO(device->interface, pipeRef,
						     bytes + retrieved, size, 0,
						     timeout, (IOAsyncCallback1)rw_completed,
						     (void *)&retrieved);
    
    if (result != kIOReturnSuccess)
      USB_ERROR_STR(-darwin_to_errno(result), "usb_bulk_read(ReadPipeTO): error reading from bulk endpoint %02x: %s",
		    ep, darwin_error_str(result));
  }
#endif

  if (result == kIOReturnSuccess)
    /* wait for write to complete */
    CFRunLoopRun();

  CFRunLoopRemoveSource(CFRunLoopGetCurrent(), cfSource, kCFRunLoopDefaultMode);

  if (result != kIOReturnSuccess || retrieved < 0)
    USB_ERROR_STR(-darwin_to_errno(result), "usb_bulk_read(ReadPipe): error reading from bulk endpoint %02x: %s",
		  ep, darwin_error_str(result));

  return retrieved;
}

/* interrupt endpoints seem to be treated just like any other endpoint under OSX/Darwin */
int usb_interrupt_write(usb_dev_handle *dev, int ep, char *bytes, int size,
	int timeout)
{
  return usb_bulk_write (dev, ep, bytes, size, timeout);
}

int usb_interrupt_read(usb_dev_handle *dev, int ep, char *bytes, int size,
	int timeout)
{
  return usb_bulk_read (dev, ep, bytes, size, timeout);
}

int usb_control_msg(usb_dev_handle *dev, int requesttype, int request,
		    int value, int index, char *bytes, int size, int timeout)
{
  struct darwin_dev_handle *device = dev->impl_info;

  io_return_t result;

#if !defined (LIBUSB_NO_TIMEOUT_DEVICE)
  IOUSBDevRequestTO urequest;
#else
  IOUSBDevRequest urequest;
#endif

  if (usb_debug >= 3)
    fprintf(stderr, "usb_control_msg: %d %d %d %d %p %d %d\n",
            requesttype, request, value, index, bytes, size, timeout);

  bzero(&urequest, sizeof(urequest));

  urequest.bmRequestType = requesttype;
  urequest.bRequest = request;
  urequest.wValue = value;
  urequest.wIndex = index;
  urequest.wLength = size;
  urequest.pData = bytes;
#if !defined (LIBUSB_NO_TIMEOUT_DEVICE)
  urequest.completionTimeout = timeout;

  result = (*(device->device))->DeviceRequestTO(device->device, &urequest);
#else
  result = (*(device->device))->DeviceRequest(device->device, &urequest);
#endif
  if (result != kIOReturnSuccess)
    USB_ERROR_STR(-darwin_to_errno(result), "usb_control_msg(DeviceRequestTO): %s", darwin_error_str(result));

  /* Bytes transfered is stored in the wLenDone field*/
  return urequest.wLenDone;
}

int usb_os_find_busses(struct usb_bus **busses)
{
  struct usb_bus *fbus = NULL;

  io_iterator_t deviceIterator;
  io_service_t usbDevice;
  io_return_t result;
  io_cf_plugin_ref_t *plugInInterface = NULL;

  usb_device_t **device;

  s_int32_t score;
  u_int32_t location;

  char buf[20];
  int i = 1;

  /* Create a master port for communication with IOKit (this should
     have been created if the user called usb_init() )*/
  if (!masterPort) {
    usb_init ();

    if (!masterPort)
      USB_ERROR(-ENOENT);
  }

  /* set up the matching dictionary for class IOUSBRootHubDevice
     and its subclasses. It will be consumed by the next call */
  if ((matchingDict = IOServiceMatching("IOUSBRootHubDevice")) == NULL) {
    darwin_cleanup ();

    USB_ERROR(-ENOMEM);
  }

  result = IOServiceGetMatchingServices(masterPort,
					matchingDict,
					&deviceIterator);
  matchingDict = NULL;

  if (result != kIOReturnSuccess)
    USB_ERROR_STR(-darwin_to_errno (result), "usb_os_find_busses(IOServiceGetMatchingServices): %s",
		  darwin_error_str(result));


  while (IOIteratorIsValid(deviceIterator) && (usbDevice = IOIteratorNext(deviceIterator))) {
    struct usb_bus *bus;

    /* Create an intermediate plug-in */
    result = IOCreatePlugInInterfaceForService(usbDevice,
					       kIOUSBDeviceUserClientTypeID,
                                               kIOCFPlugInInterfaceID,
					       &plugInInterface,
                                               &score);

    result = IOObjectRelease(usbDevice);
    if (result || !plugInInterface)
      continue;

    (*plugInInterface)->QueryInterface(plugInInterface,
				       CFUUIDGetUUIDBytes(DeviceInterfaceID),
                                       (LPVOID)&device);

    /* done with this */
    (*plugInInterface)->Stop(plugInInterface);
    IODestroyPlugInInterface (plugInInterface);
    plugInInterface = NULL;

    if (!device)
      continue;

    result = (*(device))->GetLocationID(device, &location);

    bus = malloc(sizeof(*bus));
    if (!bus)
      USB_ERROR(-ENOMEM);
    
    memset((void *)bus, 0, sizeof(*bus));
    
    sprintf(buf, "%03i", i++);
    bus->location = location;

    strncpy(bus->dirname, buf, sizeof(bus->dirname) - 1);
    bus->dirname[sizeof(bus->dirname) - 1] = 0;
    
    LIST_ADD(fbus, bus);
    
    if (usb_debug >= 2)
      fprintf(stderr, "usb_os_find_busses: Found %s\n", bus->dirname);

    (*(device))->Release(device);
  }

  IOObjectRelease(deviceIterator);

  *busses = fbus;

  return 0;
}

int usb_os_find_devices(struct usb_bus *bus, struct usb_device **devices)
{
  struct usb_device *fdev = NULL;

  io_iterator_t deviceIterator;
  io_service_t usbDevice;
  io_return_t result;
  io_cf_plugin_ref_t *plugInInterface = NULL;

  usb_device_t **device;

  s_int32_t score;
  u_int16_t address;
  u_int32_t location;
  u_int32_t bus_loc = bus->location;

  /* for use in retrieving device description */
  IOUSBDevRequest req;

  /* a master port should have been created by usb_os_init */
  if (!masterPort)
    USB_ERROR(-ENOENT);

  /* set up the matching dictionary for class IOUSBDevice and its subclasses.
     It will be consumed by the next call */
  if ((matchingDict = IOServiceMatching(kIOUSBDeviceClassName)) == NULL) {
    darwin_cleanup ();

    USB_ERROR(-ENOMEM);
  }

  result = IOServiceGetMatchingServices(masterPort, matchingDict, &deviceIterator);
  matchingDict = NULL;

  if (result != kIOReturnSuccess)
    USB_ERROR_STR(-darwin_to_errno (result), "usb_os_find_devices(IOServiceGetMatchingServices): %s", darwin_error_str(result));

  req.bmRequestType = USBmakebmRequestType(kUSBIn, kUSBStandard, kUSBDevice);
  req.bRequest = kUSBRqGetDescriptor;
  req.wValue = kUSBDeviceDesc << 8;
  req.wIndex = 0;
  req.wLength = sizeof(IOUSBDeviceDescriptor);

  while (IOIteratorIsValid (deviceIterator) && (usbDevice = IOIteratorNext(deviceIterator))) {
    /* Create an intermediate plug-in */
    result = IOCreatePlugInInterfaceForService(usbDevice, kIOUSBDeviceUserClientTypeID,
                                               kIOCFPlugInInterfaceID, &plugInInterface,
                                               &score);

    result = IOObjectRelease(usbDevice);
    if (result || !plugInInterface)
      continue;

    (*plugInInterface)->QueryInterface(plugInInterface, CFUUIDGetUUIDBytes(DeviceInterfaceID),
                                       (LPVOID)&device);

    /* done with this */
    (*plugInInterface)->Stop(plugInInterface);
    IODestroyPlugInInterface (plugInInterface);
    plugInInterface = NULL;

    if (!device)
      continue;

    result = (*(device))->GetDeviceAddress(device, (USBDeviceAddress *)&address);
    result = (*(device))->GetLocationID(device, &location);

    if (usb_debug >= 2)
      fprintf(stderr, "usb_os_find_devices: Found USB device at location 0x%08x\n", location);

    /* first byte of location appears to be associated with the device's bus */
    if (location >> 24 == bus_loc >> 24) {
      struct usb_device *dev;

      dev = malloc(sizeof(*dev));
      if (!dev) {
	USB_ERROR(-ENOMEM);
      }

      memset((void *)dev, 0, sizeof(*dev));

      dev->bus = bus;

      req.pData = &(dev->descriptor);
      result = (*(device))->DeviceRequest(device, &req);

      USB_LE16_TO_CPU(dev->descriptor.bcdUSB);
      USB_LE16_TO_CPU(dev->descriptor.idVendor);
      USB_LE16_TO_CPU(dev->descriptor.idProduct);
      USB_LE16_TO_CPU(dev->descriptor.bcdDevice);

      sprintf(dev->filename, "%03i-%04x-%04x-%02x-%02x", address,
	      dev->descriptor.idVendor, dev->descriptor.idProduct,
	      dev->descriptor.bDeviceClass, dev->descriptor.bDeviceSubClass);

      dev->dev = (USBDeviceAddress *)malloc(4);
      memcpy(dev->dev, &location, 4);

      LIST_ADD(fdev, dev);

      if (usb_debug >= 2)
	fprintf(stderr, "usb_os_find_devices: Found %s on %s at location 0x%08x\n",
		dev->filename, bus->dirname, location);
    }

    /* release the device now */
    (*(device))->Release(device);
  }

  IOObjectRelease(deviceIterator);

  *devices = fdev;

  return 0;
}

int usb_os_determine_children(struct usb_bus *bus)
{
  /* Nothing yet */
  return 0;
}

void usb_os_init(void)
{
  if (masterPort == NULL) {
    IOMasterPort(masterPort, &masterPort);
    
    gNotifyPort = IONotificationPortCreate(masterPort);
  }
}

void usb_os_cleanup (void)
{
  if (masterPort != NULL)
    darwin_cleanup ();
}

int usb_resetep(usb_dev_handle *dev, unsigned int ep)
{
  struct darwin_dev_handle *device;

  io_return_t result = -1;

  int pipeRef;

  if (!dev)
    USB_ERROR(-ENXIO);

  if ((device = dev->impl_info) == NULL)
    USB_ERROR(-ENOENT);

  /* interface is not open */
  if (!device->interface)
    USB_ERROR_STR(-EACCES, "usb_resetep: interface used without being claimed");

  if ((pipeRef = ep_to_pipeRef(device, ep)) == -1)
    USB_ERROR(-EINVAL);

  result = (*(device->interface))->ResetPipe(device->interface, pipeRef);

  if (result != kIOReturnSuccess)
    USB_ERROR_STR(-darwin_to_errno(result), "usb_resetep(ResetPipe): %s", darwin_error_str(result));

  return 0;
}

int usb_clear_halt(usb_dev_handle *dev, unsigned int ep)
{
  struct darwin_dev_handle *device;

  io_return_t result = -1;

  int pipeRef;

  if (!dev)
    USB_ERROR(-ENXIO);

  if ((device = dev->impl_info) == NULL)
    USB_ERROR(-ENOENT);

  /* interface is not open */
  if (!device->interface)
    USB_ERROR_STR(-EACCES, "usb_clear_halt: interface used without being claimed");

  if ((pipeRef = ep_to_pipeRef(device, ep)) == -1)
    USB_ERROR(-EINVAL);

  result = (*(device->interface))->ClearPipeStall(device->interface, pipeRef);

  if (result != kIOReturnSuccess)
    USB_ERROR_STR(-darwin_to_errno(result), "usb_clear_halt(ClearPipeStall): %s", darwin_error_str(result));

  return 0;
}

int usb_reset(usb_dev_handle *dev)
{
  struct darwin_dev_handle *device;
  
  io_return_t result;

  if (!dev)
    USB_ERROR(-ENXIO);

  if ((device = dev->impl_info) == NULL)
    USB_ERROR(-ENOENT);

  if (!device->device)
    USB_ERROR_STR(-ENOENT, "usb_reset: no such device");

  result = (*(device->device))->ResetDevice(device->device);

  if (result != kIOReturnSuccess)
    USB_ERROR_STR(-darwin_to_errno(result), "usb_reset(ResetDevice): %s", darwin_error_str(result));
  
  return 0;
}
