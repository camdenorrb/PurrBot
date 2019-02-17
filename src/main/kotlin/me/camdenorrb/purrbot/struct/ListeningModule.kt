package me.camdenorrb.purrbot.struct

import me.camdenorrb.kcommons.base.ModuleStruct
import me.camdenorrb.kdi.ext.inject
import me.camdenorrb.minibus.MiniBus
import me.camdenorrb.minibus.listener.MiniListener


// Kinda useless atm... :C, Thanks Java for giving Kotlin a 1 class inheritance limit
abstract class ListeningModule(val miniBus: MiniBus = inject()) : ModuleStruct(), MiniListener {

    override fun onEnable() {
        miniBus.register(this)
    }

    override fun onDisable() {
        miniBus.unregister(this)
    }

}