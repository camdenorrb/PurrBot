package me.camdenorrb.purrbot.struct

abstract class Module {

    var isEnabled = false
        protected set


    protected open fun onEnable() = Unit

    protected open fun onDisable() = Unit


    fun enable() {

        if (isEnabled) return

        onEnable()
        isEnabled = true
    }

    fun disable() {

        if (!isEnabled) return

        onDisable()
        isEnabled = false
    }

}