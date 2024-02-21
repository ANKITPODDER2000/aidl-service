package com.example.clientapplication

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.clientapplication.viewmodel.MainViewModel
import com.example.serverapp.IDataShareAidlInterface

class MainActivity : ComponentActivity(), ServiceConnection {

    private var aidlBinder: IDataShareAidlInterface? = null
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeScreen(mainViewModel) {
                if (aidlBinder == null) {
                    Toast.makeText(this, "Service not binded...", Toast.LENGTH_SHORT).show()
                } else {
                    mainViewModel.getSpValue(aidlBinder)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        if (aidlBinder == null) {
            val intent = Intent("com.example.serverapp.mainservice")
            intent.setPackage("com.example.serverapp")
            bindService(intent, this, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onServiceConnected(componentName: ComponentName, binder: IBinder) {
        Log.d("DEBUG_ANKIT", "onServiceConnected: is called")
        aidlBinder = IDataShareAidlInterface.Stub.asInterface(binder)
    }

    override fun onServiceDisconnected(p0: ComponentName?) {
        aidlBinder = null
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(this)
    }
}

@Composable
fun HomeScreen(mainViewModel: MainViewModel, btnClickListener: () -> Unit) {

    val spValue by mainViewModel.spValue.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp, 32.dp)
    ) {
        Text(
            text = "Client Application",
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Button(
            onClick = { btnClickListener() }, modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Text(text = "GET DATA")
        }
        Text(text = spValue, fontSize = 20.sp)
    }
}
