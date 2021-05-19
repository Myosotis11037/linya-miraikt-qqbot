package com.linya.mirai.service.tool

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class myTimerTask() : TimerTask(){
    override fun run(){
        GlobalScope.launch{

        }
    }
}

val date = Date()

fun timeEntrance(){
    val task = myTimerTask()
    Timer().schedule(task,Date(),)


}