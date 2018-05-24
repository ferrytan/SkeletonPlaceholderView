package com.meetferrytan.skeletonplaceholderview

import android.support.annotation.IdRes

abstract class Bone(@IdRes val viewId: Int, var customWidth: Int = -1, var customHeight: Int = -1, var hSpacing: Int = 0, var vSpacing: Int = 0)