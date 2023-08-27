package com.mycapstoneprojectsubmissiontwo.favorite.di

import com.imaginatic.mycapstoneprojectsubmissiontwo.di.AppComponent
import com.mycapstoneprojectsubmissiontwo.favorite.favorite.FavoriteFragment
import dagger.Component

@FavoriteScope
@Component(modules = [ViewFavoriteModelModule::class], dependencies = [AppComponent::class])
interface FavoriteComponent {

    @Component.Builder
    interface Builder {
        fun appComponent(appComponent: AppComponent): Builder
        fun build(): FavoriteComponent
    }

    fun inject(fragment: FavoriteFragment)
}