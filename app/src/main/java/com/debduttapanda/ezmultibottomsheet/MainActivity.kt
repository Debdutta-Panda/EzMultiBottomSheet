package com.debduttapanda.ezmultibottomsheet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.debduttapanda.ezmultibottomsheet.ui.theme.EzMultiBottomSheetTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EzMultiBottomSheetTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreen() {
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded }
    )
    val coroutineScope = rememberCoroutineScope()

    BackHandler(sheetState.isVisible) {
        coroutineScope.launch { sheetState.hide() }
    }

    var text by remember {
        mutableStateOf("")
    }

    var switch by remember {
        mutableStateOf(true)
    }

    val shape by remember {
        derivedStateOf {
            if(switch){
                CircleShape
            }
            else{
                RoundedCornerShape(24.dp)
            }
        }
    }

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            if(switch){
                BottomSheet(text){
                    text = System.currentTimeMillis().toString()
                    switch = !switch
                }
            }
            else{
                BottomSheet1(text){
                    text = System.currentTimeMillis().toString()
                    switch = !switch
                }
            }
        },
        modifier = Modifier.fillMaxSize(),
        sheetShape = shape
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 24.dp)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Multiple Bottom sheet",
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.h4,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = {
                    coroutineScope.launch {
                        if (sheetState.isVisible) sheetState.hide()
                        else sheetState.show()
                    }
                    //switch = !switch
                    text = System.currentTimeMillis().toString()
                }
            ) {
                Text(text = "Click to show bottom sheet")
            }
        }
    }
}

@Composable
fun BottomSheet(
    text: String,
    onClick: ()->Unit
) {
    Column(
        modifier = Modifier
            .padding(32.dp)
            .background(Color.Red)
    ) {
        Text(
            text = "Bottom sheet",
            style = MaterialTheme.typography.h6
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.body1
        )
        Button(onClick = onClick) {
            Text("Switch")
        }
    }
}

@Composable
fun BottomSheet1(
    text: String,
    onClick: ()->Unit
) {
    Column(
        modifier = Modifier
            .padding(32.dp)
            .background(Color.Gray)
    ) {
        Text(
            text = "Bottom sheet",
            style = MaterialTheme.typography.h6
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.body1
        )
        Button(onClick = onClick) {
            Text("Switch")
        }
    }
}