package com.mozhimen.rxk.epoxy4.util

import com.airbnb.epoxy.CarouselModelBuilder
import com.airbnb.epoxy.EpoxyModel

/**
 * @ClassName ExpoxyUtil
 * @Description TODO
 * @Author Mozhimen & Kolin Zhao
 * @Date 2024/5/10
 * @Version 1.0
 */
inline fun <T> CarouselModelBuilder.withModelsFrom(items: List<T>, modelBuilder: (T) -> EpoxyModel<*>) {
    EpoxyUtil.withModelsFrom(this, items, modelBuilder)
}

///////////////////////////////////////////////////////////////////////

object EpoxyUtil {
    /** Add models to a CarouselModel_ by transforming a list of items into EpoxyModels.
     *
     * @param items The items to transform to models
     * @param modelBuilder A function that take an item and returns a new EpoxyModel for that item. */
    @JvmStatic
    inline fun <T> withModelsFrom(carouselModelBuilder: CarouselModelBuilder, items: List<T>, modelBuilder: (T) -> EpoxyModel<*>) {
        carouselModelBuilder.models(items.map { modelBuilder(it) })
    }
}