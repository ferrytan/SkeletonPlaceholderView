# Skeleton Placeholder View [![Maven Central](https://img.shields.io/maven-central/v/com.github.ferrytan/skeletonplaceholderview.svg?label=Maven%20Central&colorB=blue)](https://search.maven.org/search?q=a:skeletonplaceholderview) [![platform](https://img.shields.io/badge/platform-android-brightgreen.svg)](https://developer.android.com/index.html)  [![license](https://img.shields.io/badge/license-Apache%202-green.svg)](https://github.com/ferrytan/SkeletonPlaceholderView/blob/master/LICENSE.md)


## Overview

A Library designed to draw a Skeleton by "skinning" the view from a provided layout. Skeleton is composed of `Bone` with different properties, library usage defines which bones to be drawn before the view is skinned. Usually used as a Placeholder while loading a data before it is populated to a View.
By using this library, creating a skeleton view is much simpler and dynamic as developers don't need to create specific skeleton layout.xml for each view, instead use this custom view to draw the shape as the specified layout.xml.

Supports the following shapes :

`RectBone`
`CircleBone`

The library is developed using Kotlin, and built for interopability with Java
> NOTE: The output of this library strongly depends on how you write your layout file

## Preview

![SkeletonPlaceholderView](https://raw.githubusercontent.com/ferrytan/SkeletonPlaceholderView/master/preview/preview-2.jpg)

Working sample on [CIAYO Comics](https://play.google.com/store/apps/details?id=com.ciayo.comics) Android App:

![SkeletonPlaceholderView](https://raw.githubusercontent.com/ferrytan/SkeletonPlaceholderView/master/preview/preview.gif)

## Setup

#### Gradle
```
    dependencies {
         implementation 'com.github.ferrytan:skeletonplaceholderview:${latestVersion}'
         ...
     }
```

> Replace `${latestVersion}` with the latest version code. See [releases](https://github.com/ferrytan/SkeletonPlaceholderView/releases).

SkeletonPlaceholderView 1.1.0 and above only supports projects that have been migrated to [androidx](https://developer.android.com/jetpack/androidx/). For more information, read Google's [migration guide](https://developer.android.com/jetpack/androidx/migrate). 
Please use version 1.0.2 for projects that are still using the old support library.

## Sample Usage
For a working implementation, please have a look at the [Sample Project](https://github.com/ferrytan/SkeletonPlaceholderView/tree/master/sample)

1. Add `SkeletonPlaceholderView` to your layout xml:
```xml
<com.meetferrytan.skeletonplaceholderview.SkeletonPlaceholderView
        android:id="@+id/skeletonPlaceholderView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:sk_bone_corner_radius_default="2dp"
        app:sk_background_color="#ffffff"
        app:sk_bone_color_default="#cccccc"
        app:sk_bone_height_default="48dp"
        app:sk_bone_width_default="100dp"/>
```


> See [`attrs.xml`](https://github.com/ferrytan/SkeletonPlaceholderView/blob/master/library/src/main/res/values/attrs.xml) for all supported attributes.

2. Skin the view programmatically by calling the method SkeletonPlaceholderView.skinView(..)
* Simple Usage (uses default RectBone)
```java
// KOTLIN OR JAVA
skeletonPlaceholderView.skinView(R.layout.item_sample_2,
            R.id.imgCover,
            R.id.txtGenre,
            R.id.txtTitle,
            R.id.frmSubscribeCount)
```
* Specific shapes
```kotlin
// KOTLIN
skeletonPlaceholderView.skinView(R.layout.item_sample_2,
            CircleBone(R.id.imgCover),
            RectBone(R.id.txtGenre),
            RectBone(R.id.txtTitle),
            RectBone(R.id.frmSubscribeCount))
```
```java
// JAVA
skeletonPlaceholderView.skinView(R.layout.item_sample_2,
            new CircleBone(R.id.imgCover),
            new RectBone(R.id.txtGenre),
            new RectBone(R.id.txtTitle),
            new RectBone(R.id.frmSubscribeCount))
```
* Custom Shapes using Builder Pattern
```kotlin
// KOTLIN
// Common builder pattern
skeletonPlaceholderView.skinView(R.layout.item_sample_2,
            CircleBone.Builder(R.id.imgCover)
            	.spacing(0)
                .build(),
            RectBone.Builder(R.id.txtGenre)
            	.cornerRadius(4f)
            	.customHeight(200)
            	.customWidth(100)
            	.horizontalSpacing(2)
            	.verticalSpacing(4)
            	.build(),
            RectBone.Builder(R.id.txtTitle)
            	.build(),
            RectBone.Builder(R.id.frmSubscribeCount)
            	.build())

// DSL
skeletonPlaceholderView.skinView(R.layout.item_sample_2,
            CircleBone.Builder(R.id.imgCover)
            	.spacing(0)
                .build(),
            RectBone.Builder(R.id.txtGenre)
                .apply {
                    cornerRadius(4f)
                    customHeight(200)
                    customWidth(100)
                    horizontalSpacing(2)
                    verticalSpacing(4)
                }.build(),
            RectBone.Builder(R.id.txtTitle)
            	.build(),
            RectBone.Builder(R.id.frmSubscribeCount)
            	.build())

```
```java
// JAVA
skeletonPlaceholderView.skinView(R.layout.item_sample_2,
            new CircleBone.Builder(R.id.imgCover)
            	.spacing(0)
                .build(),
            new RectBone.Builder(R.id.txtGenre)
                .cornerRadius(4f)
                .customHeight(200)
                .color(Color.parseColor("#ff0000"))
                .customWidth(100)
                .horizontalSpacing(2)
                .verticalSpacing(4)
                .build(),
            new RectBone.Builder(R.id.txtTitle)
                .build(),
            new RectBone.Builder(R.id.frmSubscribeCount)
                .build())
```
See a complete usage in [`sample`](https://github.com/ferrytan/SkeletonPlaceholderView/tree/master/sample) code.
> Once again, the output of this library STRONGLY DEPENDS on how you write your layout file

Usage in sample app is improved by using [Facebook](https://github.com/facebook)'s great library [shimmer-android](https://github.com/facebook/shimmer-android) for a shimmering effect over the `SkeletonPlaceholderView`
## Developed By

**Ferry Irawan**

<irawan.ferry.tan@gmail.com>

[http://meetferrytan.com/](http://meetferrytan.com/)

## Projects/Apps using SkeletonPlaceholderView

- <a href="https://play.google.com/store/apps/details?id=com.ciayo.comics">CIAYO Comics</a>
- <a href="https://play.google.com/store/apps/details?id=com.meetferrytan.sksample">Sample App</a>

I'd be happy to know if you're using this library to your app, feel free to contact me and i'll add it to the list

## Thanks
Special thanks to:
- [https://github.com/facebook/shimmer-android](https://github.com/facebook/shimmer-android) for the awesome Shimmer in improving the output of this library
- [https://github.com/android/android-ktx](https://github.com/android/android-ktx) for the useful extensions used in this library
## License

```
Copyright 2018 Ferry Irawan

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
