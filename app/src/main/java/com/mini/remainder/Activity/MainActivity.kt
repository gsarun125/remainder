package com.mini.remainder.Activity


import android.app.AlarmManager
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.RemoteViews
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
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
import java.util.Locale
import java.util.Random


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
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        insert()
    }
    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.anim_scale_in,R.anim.anim_scale_out)
    }

    open fun Check_Notification_Settings() {
        val notificationManager = NotificationManagerCompat.from(this)
        if (!notificationManager.areNotificationsEnabled()) {
            openNotificationSettings()
            return
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && isChannelBlocked(NOTIFICATION_CHANNEL_ID)
        ) {
            openChannelSettings(NOTIFICATION_CHANNEL_ID)
            return
        }
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

                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val date = sdf.format(it)

                //date=  materialDatePicker.getHeaderText().format(0)
                //println(date)

                val sdf1 = SimpleDateFormat("dd", Locale.getDefault())
                val sdf2 = SimpleDateFormat("MM", Locale.getDefault())
                val sdf3 = SimpleDateFormat("yyyy", Locale.getDefault())

                D=  sdf1.format(it).toInt()
                M=  sdf2.format(it).toInt()
                Y=  sdf3.format(it).toInt()

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
                val datetime = Calendar.getInstance()
                val c = Calendar.getInstance()
                if (D!=0 && M!=0 && Y!=0) {
                    datetime[Calendar.DATE] = D
                    datetime[Calendar.MONTH] = M - 1
                    datetime[Calendar.YEAR] = Y
                    datetime[Calendar.HOUR_OF_DAY] = hour.toString().toInt()
                    datetime[Calendar.MINUTE] = minute.toString().toInt()

                if (datetime.getTimeInMillis() >= c.getTimeInMillis()) {

                    H = hour.toString().toInt()
                    Min = minute.toString().toInt()
                    binding.time.editText?.setText(time)

                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Invalid Time", Toast.LENGTH_LONG).show();
                }
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Enter the Date", Toast.LENGTH_LONG).show();
                }

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

            if (D!=0 && M!=0 && Y!=0 && (H!=0 || Min!=0))
            {
                val calender=Calendar.getInstance()
                println(D)
                println(M)
                println(Y)
                calender.set(Y,M-1,D,H,Min)
                time = calender.getTimeInMillis() - calender.getTimeInMillis() % 60000
                println(calender.getTimeInMillis())


                var random = Random()
                var Request_ID=random.nextInt(10000000)

                db.insertData(Title,time,Request_ID)

                println(Request_ID)
                set(getNotification(Title,Request_ID),time,Request_ID)


                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish();

            }
            else{
                Toast.makeText(this,"Enter the Value",Toast.LENGTH_SHORT).show()
            }
        }

    }


    fun getNotification(title:String,Request_ID:Int): Notification {



        println(Request_ID);

        val intent = Intent(this, ShowActivity::class.java)
        intent.putExtra("a",title)
        val pendingIntent = PendingIntent.getActivity(this, Request_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT)


        var contentView = RemoteViews(packageName, R.layout.notify)
        contentView.setImageViewResource(R.id.image, R.drawable.pic)
        contentView.setTextViewText(R.id.title, "Scheduled Notification")
        contentView.setTextViewText(R.id.text, "$title")


        var builder = NotificationCompat.Builder(this, default_notification_channel_id)
        builder.setSmallIcon(R.drawable.ic_launcher_foreground)
        builder.setAutoCancel( true )
        builder.setChannelId( NOTIFICATION_CHANNEL_ID )
        builder.setContentIntent(pendingIntent)
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT)
        builder.setGroup("Schedule")
        builder.setGroupAlertBehavior(NotificationCompat.GROUP_ALERT_CHILDREN)
        builder.setContent(contentView)
        return builder.build()

    }

    public fun openNotificationSettings() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            var intent=Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
            intent.putExtra(Settings.EXTRA_APP_PACKAGE,packageName)
            startActivity(intent)
        }else
        {
            var  intent=Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.parse("package:"+packageName))
            startActivity(intent)
        }

    }


    fun openChannelSettings(channelID:String){
        val intent = Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS)
        intent.putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
        intent.putExtra(Settings.EXTRA_CHANNEL_ID, channelID)
        startActivity(intent)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun isChannelBlocked(channel1Id: String): Boolean {
        val manager = getSystemService(NotificationManager::class.java)
        val channel = manager.getNotificationChannel(channel1Id)

        return channel != null &&
                channel.importance == NotificationManager.IMPORTANCE_NONE

    }

    fun set(a:Notification,time:Long,Request_ID: Int){

        println(Request_ID);


        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlertReceiver::class.java)
        intent.putExtra(AlertReceiver. NOTIFICATION_ID , 1 )
        intent.putExtra(AlertReceiver. NOTIFICATION , a)
        pendingIntent = PendingIntent.getBroadcast(this,Request_ID , intent, PendingIntent.FLAG_UPDATE_CURRENT)
        alarmManager!!.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, time, pendingIntent)


        val formatter = SimpleDateFormat("dd/MM/yyyy hh:mm:ss aa")

        dateString = formatter.format(Date(time))
        Toast.makeText(applicationContext, dateString, Toast.LENGTH_LONG).show()


    }
    fun cancel(Request_ID: Int) {
        println(Request_ID)
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val myIntent = Intent(applicationContext, AlertReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(applicationContext, Request_ID, myIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        alarmManager.cancel(pendingIntent)
    }
    override fun onBackPressed() {
        val i = Intent(this, HomeActivity::class.java)
        startActivity(i)
        finish()
    }


}