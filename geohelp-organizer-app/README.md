# GeoHelp-Organizer-App

The frontend part of the GeoHelp project.
A website which allows an event organizer to register a event, with a Event-Name, Start date, end date and a location.
A QR-Code and a passphrase will be provided to the organizer.
The QR-Code can be scanned with [GeoHelp-User-App](https://gitlab.ethz.ch/aeggerth/geohelp-user-app).

With the passphrase and the event name the organizer can start to monitor a event. A heatmap will show to where emergencies were triggered. By clicking on the map the organizer can approve emergencies, then GeoHelp users nearby will be informed that there is an emergency.  

## Getting Started
### Prerequisites
You need [nodejs](https://nodejs.org/en/) and a global [angular](https://angular.io/guide/setup-local) installation to build the project.

### Installing
Clone this repository and set the backend IP-Address of the backend in the `src/app/backend.service.ts`.
```
git clone https://gitlab.ethz.ch/aeggerth/geohelp-organizer-app
cd path/to/repo
```

Run angular development server
```
ng serve --watch
```

Build project
```
ng build
```


## Build With
* [Angular](https://angular.io/) - Webapp framework
* [Leaflet](https://leafletjs.com/) - Open-source JavaScript library for mobile-friendly interactive maps
* [angularx-qrcode](https://www.npmjs.com/package/angularx-qrcode) - Angular QR Code Generator
