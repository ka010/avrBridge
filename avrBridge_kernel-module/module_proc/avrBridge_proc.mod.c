#include <linux/module.h>
#include <linux/vermagic.h>
#include <linux/compiler.h>

MODULE_INFO(vermagic, VERMAGIC_STRING);

struct module __this_module
__attribute__((section(".gnu.linkonce.this_module"))) = {
 .name = KBUILD_MODNAME,
 .init = init_module,
#ifdef CONFIG_MODULE_UNLOAD
 .exit = cleanup_module,
#endif
 .arch = MODULE_ARCH_INIT,
};

static const struct modversion_info ____versions[]
__used
__attribute__((section("__versions"))) = {
	{ 0xfcb2e382, "struct_module" },
	{ 0x3ddb5f79, "usb_register_driver" },
	{ 0x268c0012, "create_proc_entry" },
	{ 0x8feddcce, "proc_mkdir" },
	{ 0xec359ee6, "usb_register_dev" },
	{ 0xcc104b36, "usb_get_dev" },
	{ 0x64cd5d16, "init_waitqueue_head" },
	{ 0x67e71b77, "__mutex_init" },
	{ 0x83800bfa, "kref_init" },
	{ 0xf2399fec, "kmem_cache_alloc" },
	{ 0x1f2658dc, "kmalloc_caches" },
	{ 0x2f287f0d, "copy_to_user" },
	{ 0xd0d8621b, "strlen" },
	{ 0x3c2c5af5, "sprintf" },
	{ 0xb9bb9953, "usb_control_msg" },
	{ 0xbf381fd1, "usb_autopm_get_interface" },
	{ 0x9775cdc, "kref_get" },
	{ 0xb4c6f333, "usb_find_interface" },
	{ 0xd5b037e1, "kref_put" },
	{ 0x577cc638, "mutex_unlock" },
	{ 0xbf2625b4, "usb_autopm_put_interface" },
	{ 0x8aaf8654, "mutex_lock" },
	{ 0xb72397d5, "printk" },
	{ 0x5ab70adb, "dev_driver_string" },
	{ 0xbeb4c9be, "remove_proc_entry" },
	{ 0xff78ae9a, "usb_deregister_dev" },
	{ 0x37a0cba, "kfree" },
	{ 0x72f60e02, "usb_put_dev" },
	{ 0x39ec398d, "usb_deregister" },
	{ 0xb4390f9a, "mcount" },
};

static const char __module_depends[]
__used
__attribute__((section(".modinfo"))) =
"depends=usbcore";

MODULE_ALIAS("usb:v16C0p05DCd*dc*dsc*dp*ic*isc*ip*");

MODULE_INFO(srcversion, "E1A0C607909EAE878DE9F35");
