package com.mini.remainder.Activity

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlarmManager
import android.app.Notification
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.RemoteViews
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.mini.remainder.DataBase.DataBaseHandler
import com.mini.remainder.R
import com.mini.remainder.databinding.ActivityMainBinding
import com.mini.remainder.model.AlertReceiver
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.TimeZone


open class MainActivity : AppCompatActivity(){


    val NOTIFICATION_CHANNEL_ID = "10001"
    private val default_notification_channel_id = "default"

    lateinit var binding: ActivityMainBinding

    var alarmManager: AlarmManager? = null
    var pendingIntent: PendingIntent? = null
    var dateString: String? = null
    var Y:Int =0
    var M:Int=0
    var D:Int=0
    var H:Int=0
    var Min:Int=0
     var time:Long = 0
    lateinit var date:String

    val context = this
    protected var db = DataBaseHandler(context)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        insert()
    }
    private  fun  insert(){

        binding.date.editText?.setOnClickListener {


            val calendarConstraintBuilder = CalendarConstraints.Builder()
            calendarConstraintBuilder.setValidator(DateValidatorPointForward.now())
            val materialDatePickerBuilder: MaterialDatePicker.Builder<*> =
                MaterialDatePicker.Builder.datePicker()
            materialDatePickerBuilder.setTitleText("SELECT A DATE")
            materialDatePickerBuilder.setCalendarConstraints(calendarConstraintBuilder.build())
            val materialDatePicker = materialDatePickerBuilder.build()

            materialDatePicker.addOnPositiveButtonClickListener {

                date=  Date().format(0)

                println(date)
                D=  Date().format(1).toInt()
                M=  Date().format(2).toInt()
                Y=  Date().format(3).toInt()

                binding.date.editText?.setText(date)

            }

            materialDatePicker.show(supportFragmentManager, "DATE_PICKER_TAG")
        }
        binding.time.editText?.setOnClickListener {
            val timePicker=MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_12H).build()
            timePicker.addOnPositiveButtonClickListener {
                val minute =
                    if (timePicker.minute in 0..9)
                        "0${timePicker.minute}"
                    else
                        timePicker.minute
                val hour = if (timePicker.hour in 0..9)
                    "0${timePicker.hour}"
                else
                    timePicker.hour
                val time="${hour}:${minute}"

                H=hour.toString().toInt()
                Min=minute.toString().toInt()

                binding.time.editText?.setText(time)

            }
            timePicker.show(supportFragmentManager, null)

        }
        binding.set.setOnClickListener {
            val Title:String=binding.alertTitle.getText().toString()


            println(Title)
            println(D)
            println(M)
            println(Y)
            println(H)
            println(Min)

            if (D!=0 && M!=0 && Y!=0 && (H!=0 || Min!=0) && Title!=null)
            {

                set(getNotification(Title),D,M,Y,H,Min)

                db.insertData(Title,time)

                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)

            }
            else{
                Toast.makeText(this,"Enter the Value",Toast.LENGTH_SHORT).show()
            }
        }

    }

    @SuppressLint("RemoteViewLayout")
    private fun getNotification(title:String): Notification {



        val intent = Intent(this, ShowActivity::class.java)
        intent.putExtra("a",title)
        val pendingIntent = PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        var contentView = RemoteViews(packageName, R.layout.notify)
        contentView.setImageViewResource(R.id.image, R.drawable.pic)
        contentView.setTextViewText(R.id.title, "Scheduled Notification")
        contentView.setTextViewText(R.id.text, "$title")


        var builder = NotificationCompat.Builder(this, default_notification_channel_id)
        builder.setSmallIcon(R.drawable.ic_launcher_foreground)
        builder.setAutoCancel( true )
        builder.setChannelId( NOTIFICATION_CHANNEL_ID )
        builder.setContentIntent(pendingIntent)
        builder.setContent(contentView)
        return builder.build()

    }

    fun set(a:Notification,D:Int,M:Int,Y:Int,H:Int,Min: Int){
        val calender=Calendar.getInstance()
        println(D)
        println(M)
        println(Y)

        calender.set(Y,M-1,D,H,Min)

        time = calender.getTimeInMillis() - calender.getTimeInMillis() % 60000
        println(calender.getTimeInMillis())



        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlertReceiver::class.java)
        intent.putExtra(AlertReceiver. NOTIFICATION_ID , 1 )
        intent.putExtra(AlertReceiver. NOTIFICATION , a)
        pendingIntent = PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_IMMUTABLE)
        alarmManager!!.setExact(AlarmManager.RTC_WAKEUP, time, pendingIntent)


        val formatter = SimpleDateFormat("dd/MM/yyyy hh:mm:ss aa")

        dateString = formatter.format(Date(time))
        Toast.makeText(applicationContext, dateString, Toast.LENGTH_LONG).show()


    }

    open fun restartActivity(activity: Activity) {
        if (Build.VERSION.SDK_INT >= 11) {
            activity.recreate()
        } else {
            activity.finish()
            activity.startActivity(activity.intent)
        }
    }


    fun Date.format(i:Int ): String {

        if (i==0)
            return SimpleDateFormat("dd/MM/yyyy").format(this)
        else if (i==1)
            return SimpleDateFormat("dd").format(this)
        else if(i==2)
            return SimpleDateFormat("MM").format(this)
        else
            return SimpleDateFormat("yyyy").format(this)
    }
}