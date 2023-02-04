package com.project.countries.viewmodel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.disposables.CompositeDisposable
import java.lang.reflect.Constructor

abstract class ViewHolder {
    companion object {
        fun <VH : ViewHolder> createViewHolder(vhClass: Class<VH>): VH {
            val viConstructor: Constructor<VH> = vhClass.getConstructor()
            return viConstructor.newInstance()

        }

    }

    protected var compositeDisposable = CompositeDisposable()

    protected var rootView: View? = null

    abstract fun onCreateView(inflater: LayoutInflater, parentView: ViewGroup?): View?

    internal fun createView(inflater: LayoutInflater, parentView: ViewGroup?): View? {
        rootView = onCreateView(inflater, parentView)
        return rootView
    }

    internal fun destroyView(){
        rootView = null
        compositeDisposable.clear()
    }

}