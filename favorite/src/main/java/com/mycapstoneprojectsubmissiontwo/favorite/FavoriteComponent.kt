package com.mycapstoneprojectsubmissiontwo.favorite

import com.imaginatic.mycapstoneprojectsubmissiontwo.di.AppComponent
import dagger.Component

@FavoriteScope
@Component(dependencies = [AppComponent::class])
interface FavoriteComponent {

    @Component.Builder
    interface Builder {
        fun appComponent(appComponent: AppComponent): Builder
        fun build(): FavoriteComponent
    }

    fun inject(fragment: FavoriteFragment)
}