# RollingBanner
Change your Teamspeak server banner after a certain amount of time.

## 💡 How does it work?

You add some banner objects to the banner.json config, which then are cycled through and displayed on your Teamspeak server. 

## 🚀 Gettings started

Just download the latest release that's compatible with your TSQPF version and copy it into its plugin directory. After you've done that, either reload or restart your framework instance in order to get it loaded and initiated.

## ⚙️ banners.json

As this plugin uses JSON as config language, this plugin can not use the frameworks config system.
Here's a list of all config values and what they do:

KEY | DATATYPE | DESCRIPTION

- **displayTime** : [Integer] Sets the time the banner is going to be displayed before being replaced by the next one.
- **imageSrc** : [String] The URL to your banner image.
- **redirectUrl** : [String] The URL a user gets redirected to if they click on the banner.


## 📁 Directory Tree

RollingBanner/<br>
├── plugin.conf<br>
└── banners.json<br>

## 📜 Vortexdata Certification

This plugin is developed by VortexdataNET for the Teamspeak Query Plugin Framework. Every release is being tested for any bugs, its performance or security issues. You are free to use, modify or redistribute the plugin.
