package com.linya.mirai.service.tool

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

fun timeEntrance(){
    Timer().schedule(object:TimerTask(){
        override fun run(){
            GlobalScope.launch{

            }
        }
    },Date(),60000)

}