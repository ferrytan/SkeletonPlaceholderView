package com.meetferrytan.skeletonplaceholderview

import android.support.annotation.IdRes

/**
 * Base Bone class.
 *
 * @property viewId id reference of the View to be drawn as a bone
 * @property customWidth custom bone width, by default (-1) uses the view's original width OR [SkeletonPlaceholderView.boneDefaultWidth]
 * @property customWidth custom bone height, by default (-1) uses the view's original height [SkeletonPlaceholderView.boneDefaultHeight]
 * @property hSpacing horizontal spacing inside of the bone
 * @property vSpacing vertical spacing inside of the bone
 * @constructor creates bone with default properties
 */
abstract class Bone(@IdRes val viewId: Int, var customWidth: Int = -1, var customHeight: Int = -1, var hSpacing: Int = 0, var vSpacing: Int = 0)