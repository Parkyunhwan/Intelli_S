Situm Wayfinding Getting Started
=======================

This is a sample Android application built with Situm Wayfinding Module.
Situm Wayfinding Module has been designed to create indoor location applications in the simplest way. It has been built in the top of the Situm SDK and its functionalities has been widely tested. If you are interested in building applications using the Situm SDK, please refer to [Situm Android SDK Sample app](https://github.com/situmtech/situm-android-getting-started).

With the samples app you will be able to:

  1. Load a Situm Map with all the buildings of your account and start the positioning for a selected building, using the `SitumMapView` and `SitumMap` objects.
  2. Set listeners to receive notifications about user location, buildings, POIs and routes.
  3. Get more control of the map loading using the underlying `SitumMapsLibrary` object.
  4. Delegate `onBackPressed()` events for convenience.


# Table of contents
#### [Introduction](#introduction)
#### [Setup](#configure-project)
1. [Step 1: Configure your Android project](#step-1-configure-your-android-project)
2. [Step 2: Set your credentials](#step-2-set-your-credentials)
3. [Step 3: Configure Third Party Dependencies](#step-3-configure-third-party-dependencies)

#### [Samples](#samples)

1. [SitumMapView](#sitummapview)
2. [Install listeners using SitumMap](#install-listeners-using-sitummap)
3. [Load the map using SitumMapsLibrary](#load-the-map-using-sitummapslibrary)
4. [Delegate onBackPressed events](#delegate-onbackpressed-events)
5. [Set your own theme in the UI](#set-your-own-theme-in-the-ui)
6. [Remove the `situm_search_view` feature](#remove-the-situm_search_view-feature)
7. [Customize location, navigation and directions requests](#customize-location-navigation-and-directions-requests)
8. [Customize the default UI](#customize-the-default-ui)
9. [Auto start positioning](#auto-start-positioning)
10. [Single building mode](#single-building-mode)

#### [More information](#more-information)
#### [Support information](#support-information)

### Introduction

Situm SDK is a set of utilities that allow any developer to build location based apps using Situm's indoor positioning system.
Among many other capabilities, apps developed with Situm SDK will be able to:

1. Obtain information related to buildings where Situm's positioning system is already configured:
floor plans, points of interest, geotriggered events, etc.
2. Retrieve the location of the smartphone inside these buildings (position, orientation, and floor
where the smartphone is).
3. Compute a route from a point A (e.g. where the smartphone is) to a point B (e.g. any point of
interest within the building).
4. Trigger notifications when the user enters a certain area.

In this tutorial, we will guide you step by step to set up your first Android application using the **Situm Wayfinding Module**, which has been built in the top of the **Situm SDK** to extremely easy the development of indoor positioning Android applications.

Before starting to write code, we recommend you to set up an account in our Dashboard
(https://dashboard.situm.es), retrieve your API KEY and configure your first building.

1. Go to the [sign in form](http://dashboard.situm.es/accounts/register) and enter your username
and password to sign in.
2. Go to the [account section](https://dashboard.situm.es/accounts/profile) and on the bottom, click
on "generate one" to generate your API KEY.
3. Go to the [buildings section](http://dashboard.situm.es/buildings) and create your first building.
4. Download [Situm Mapping Tool](https://play.google.com/store/apps/details?id=es.situm.maps) Android application. With this application you will be able to configure and test Situm's indoor positioning system in your buildings.

Perfect! Now you are ready to develop your first indoor positioning application.

### Configure project

#### Step 1: Configure your Android project

First of all, you must configure Situm SDK and Situm Wayfinding Module in your Android project. This has been already done for you in the sample application, but nonetheless we will walk you through the process.

* Add our maven repository to the **project** *build.gradle*:

```groovy
allprojects {
    repositories {
        maven { url "https://repo.situm.es/artifactory/libs-release-local" }
    }
}
```

* Now add the Situm Wayfinding Module dependency into the *dependencies* section of the **app** *build.gradle*.

```groovy
    implementation ('es.situm:situm-wayfinding:0.2.0-alpha@aar') {
        transitive = true
    }
```

It's important to add the `transitive = true` property to download the inherited dependencies.

* Set compatibility with Java 8 in your **app** *build.gradle* file:
```groovy
android {
    ...
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
}
```

* Finally, add multidex compatibility to your **app** *build.gradle*:
```groovy
android {
    defaultConfig {
        ...
        minSdkVersion 16
        targetSdkVersion Y
        multiDexEnabled true
    }
    ...
}
```

Please remember that if your `minSdkVersion` is set to 20 or lower, you must use the multidex support library as follows:
```groovy
dependencies {
  implementation 'com.android.support:multidex:1.0.3'
}
```

#### Step 2: Set your credentials

There are two ways to set the credentials, in the `AndroidManifest.xml` file or programmatically.

##### Option 1: `AndroidManifest.xml` file
You can set the credentials (user and API key) in the `application` element of your `AndroidManifest.xml` file adding the next `meta-data` fields:
```xml
<meta-data
  android:name="es.situm.maps.API_USER"
  android:value="YOUR_API_USER" />
<meta-data
  android:name="es.situm.maps.API_KEY"
  android:value="YOUR_API_KEY" />
```

##### Option 2: programmatically
When using the `SitumMapsLibrary object`, you can set the the user and API key with:
```java
librarySettings.setApiKey(USER, API_KEY);
```
Or with user and password:
```java
librarySettings.setUserPass(USER, PASSWORD);
```

#### Step 3: Configure Third Party Dependencies

* Get your Google Maps API key by following the instructions detailed in [Google Maps Get Started](https://developers.google.com/maps/documentation/android-sdk/start#get-key).
Add the API key to your `AndroidManifest.xml`:
```xml
<meta-data
  android:name="com.google.android.geo.API_KEY"
  android:value="AIza..." />
```

* If you are targeting Android Pie devices, add Apache Http Legacy to your `AndroidManifest.xml`:
```xml
<uses-library
  android:name="org.apache.http.legacy"
  android:required="false"/>
```

* By default, the wayfinding library will include a `ToolBar` with the situm search feature.
    * Tell your activity or app theme that you already use one by adding these lines to your styles.xml:
    ```xml
    <item name="windowActionBar">false</item>
    <item name="windowNoTitle">true</item>
    ```
    * Or remove the Situm search toolbar setting to `false` the `situm_has_search_view` property of your `SitumMapView`:
    ```xml
    <es.situm.wayfinding.SitumMapView
        ...
        app:situm_has_search_view="false" />
	```

* Make sure to extend [AppCompatActivity](https://developer.android.com/reference/android/support/v7/app/AppCompatActivity).
If you chose not to use the `situm_search_view` feature you can just extend [FragmentActivity](https://developer.android.com/reference/android/support/v4/app/FragmentActivity).

* Right now, the `minSdkVersion` must be `16` or greater.

## Samples

We have created some samples that show different use cases of this module

### SitumMapView

The simplest way to use our module is by creating an empty activity and including `SitumMapView` in the layout file.

For more information, please refer to `ActivitySampleSimpleMap.java`.

### Install listeners using SitumMap

If you want to get notified about what happens inside the module, your activity should implement the `SitumMapView.OnMapReadyCallback` interface:

For more information, please refer to `ActivitySampleLogEvents.java`

### Load the map using SitumMapsLibrary

Under some circumstances you may prefer to control the exact moment at which the underlying maps fragment is loaded. If that is your case, just use the `SitumMapsLibrary` object as described in `ActivitySampleUsingLibrary.java`.

### Delegate onBackPressed events

The Situm Map implements some convenience actions for `onBackPressed` Android events:

* When there is an active building selected: zoom out to see building markers again.
* When a POI is selected: deselects the current POI.
* When a route has been requested: cancels the current route.
* When an event is triggered: closes the event dialog.

If you want to keep this behaviour in your application, just delegate each `onBackPressed` as described in `ActivitySampleDelegateBackEvents.java`.

### Set your own theme in the UI

You can use your own logo and color scheme in the module.
1. Define an organization theme in your Situm Dashboard.
2. Tell the module to use the organization theme. For that, see `ActivitySampleDashboardTheme.java`.

### Remove the `situm_search_view` feature

If you want to remove the default `situm_search_view` feature, please refer to `ActivitySampleNoSearchView.java`.

### Customize location, navigation and directions requests

The wayfinding module builds default location, navigation and directions requests. You may want to configure those requests before they are dispatched to the underlying SitumSDK.
If that is your case, please refer to `ActivitySampleCustomizeRequests.java`.

### Customize the default UI

Sometimes you may want to use your own UI elements over the module, using Situm Wayfinding it is possible to add your own controllers for positioning, for example a floor selector. If you want to know more about how to do this, please visit `ActivitySampleCustomizeUI.java`.

### Auto start positioning

For some use cases, you may be interested in starting the positioning system automatically instead of using the UI button. To activate this option, you just need to enable a property in your activity's xml file. If you are interested in this, please refer to the `ActivitySampleAutostartPositioning.java`.

### Single building mode

Situm Wayfinding is designed to work with as many buildings as you want to configure in your dashboard account. However, you may want to restrict that access for your users. If that is your case, you can learn how to restrict the user to just one building using the "Single Building Mode". You can learn how to activate it checking the xml used in the example `ActivitySampleOneBuildingMode.java`.

## More information

More info is available at our [Developers Page](http://developers.situm.es/pages/wayfinding/).

## Support information

For any question or bug report, please send an email to [support@situm.es](mailto:support@situm.es)
