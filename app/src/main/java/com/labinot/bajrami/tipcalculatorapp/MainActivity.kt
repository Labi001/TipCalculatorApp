package com.labinot.bajrami.tipcalculatorapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.labinot.bajrami.tipcalculatorapp.composable.InputField
import com.labinot.bajrami.tipcalculatorapp.composable.RoundButton
import com.labinot.bajrami.tipcalculatorapp.ui.theme.TipCalculatorAppTheme
import com.labinot.bajrami.tipcalculatorapp.util.CalculateTotalPerPerson
import com.labinot.bajrami.tipcalculatorapp.util.CalculateTotalTip

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TipCalculatorAppTheme {

                val isSystemInDarkMode = isSystemInDarkTheme()
                val systemUiController = rememberSystemUiController()

                SideEffect {

                    systemUiController.setSystemBarsColor(
                        color = Color(0xFF826F91),
                        darkIcons = isSystemInDarkMode
                    )

                }


                MyApp()

            }
        }
    }
}

@Composable
fun MyApp(){

    val totalPerPersonState = remember {

        mutableStateOf(0.0)
    }

    val splitByState = remember {

        mutableStateOf(1)
    }

    val tipAmountState = remember {
        mutableStateOf(0.0)
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {

        Column(horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top) {

            TopHeader(totalPerPerson = totalPerPersonState.value)
            DetailBox(totalPersonState = totalPerPersonState,
                splitByState = splitByState,
                tipAmountState = tipAmountState)

        }


    }


}


@Composable
fun TopHeader(totalPerPerson:Double) {

    Surface(modifier = Modifier
        .padding(20.dp)
        .fillMaxWidth()
        .height(150.dp),
        color = Color(0xFFE3D1F1),
        shape = RoundedCornerShape(12.dp),
        shadowElevation = 8.dp
        ) {

        Column(horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {

            val total = "%.2f".format(totalPerPerson)
            Text(text = "Total per Person",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineSmall)

            Text(text = "$$total",
                fontSize = 25.sp,
                fontWeight = FontWeight.ExtraBold,
                style = MaterialTheme.typography.headlineMedium)

        }

    }

}

@SuppressLint("RememberReturnType")
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DetailBox(modifier: Modifier = Modifier,
              splitByState:MutableState<Int>,
              tipAmountState:MutableState<Double>,
              totalPersonState:MutableState<Double>,
    onValueChange:(String) -> Unit = {}) {

    val getInputUser = remember {

        mutableStateOf("")
    }

    val validState = remember(getInputUser.value) {

           getInputUser.value.trim().isNotEmpty()
    }

    val sliderPositionSate = remember {

        mutableStateOf(0f)
    }

    val tipPercentage = (sliderPositionSate.value * 100).toInt()

    val keyBoardController = LocalSoftwareKeyboardController.current

    Surface(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 10.dp, end = 10.dp),
        color = Color.Transparent,
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(2.dp, Color.LightGray),
    ) {

        Column(horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top) {

            InputField(valueState = getInputUser,
                labelId = "Enter Bill" ,
                enabled = true,
                isSingleLine = true,
                onActions = KeyboardActions{

                    if(!validState)
                        return@KeyboardActions

                    onValueChange(getInputUser.value.trim())

                    keyBoardController?.hide()



                }
            )

            if(validState){

                Row(modifier = Modifier.padding(start = 15.dp),
                    horizontalArrangement = Arrangement.Start) {

                    Text(text = "Split:",
                        modifier = Modifier.align(
                            alignment = Alignment.CenterVertically
                        ),
                        fontWeight = FontWeight.Bold)

                    Spacer(modifier = Modifier.width(150.dp))

                    Row(modifier = Modifier.padding(horizontal = 3.dp),
                        horizontalArrangement = Arrangement.End) {

                        RoundButton(
                            modifier = Modifier.size(40.dp),
                            imageVector = painterResource(id = R.drawable.ic_minus),
                            onClick = { splitByState.value =
                               if(splitByState.value >1) splitByState.value - 1 else 1

                                totalPersonState.value = CalculateTotalPerPerson(totalBill = getInputUser.value.toDouble(),
                                    splitBy = splitByState.value,tipPercentage = tipPercentage)

                            })
                       Spacer(modifier = Modifier.width(15.dp))

                        Text(text = "${splitByState.value}",
                            modifier = Modifier.align(Alignment.CenterVertically))
                        Spacer(modifier = Modifier.width(15.dp))

                        RoundButton(
                            modifier = Modifier.size(40.dp),
                            imageVector = painterResource(id = R.drawable.ic_plus),
                            onClick = { splitByState.value =
                                     splitByState.value + 1

                                totalPersonState.value = CalculateTotalPerPerson(totalBill = getInputUser.value.toDouble(),
                                    splitBy = splitByState.value,tipPercentage = tipPercentage)

                            })
                    }

                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(modifier = Modifier.padding(start = 15.dp),
                    horizontalArrangement = Arrangement.Start) {

                    Text(text = "Tip:",
                        modifier = Modifier.align(
                            alignment = Alignment.CenterVertically
                        ),
                        fontWeight = FontWeight.Bold)

                    Spacer(modifier = Modifier.width(210.dp))

                  Text(text = "$ ${tipAmountState.value} ")

                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(text = " $tipPercentage % ",
                    modifier = Modifier.align(Alignment.CenterHorizontally))

                Spacer(modifier = Modifier.height(20.dp))

                Slider(value = sliderPositionSate.value ,
                    onValueChange = { newValue ->

                        sliderPositionSate.value = newValue

                        tipAmountState.value = CalculateTotalTip(totalBill = getInputUser.value.toDouble() , tipPercentage = tipPercentage)

                        totalPersonState.value = CalculateTotalPerPerson(totalBill = getInputUser.value.toDouble(),
                            splitBy = splitByState.value,tipPercentage = tipPercentage)
                    },
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                    steps = 0,
                    colors = SliderDefaults.colors(

                        thumbColor =  Color(0xFF826F91),
                        activeTrackColor = Color(0xFFE3D1F1)

                    ))



            }else{

                Box { }
            }

        }
        
    }

}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TipCalculatorAppTheme {

        MyApp ()

    }
}