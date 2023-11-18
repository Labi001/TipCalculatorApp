package com.labinot.bajrami.tipcalculatorapp.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.labinot.bajrami.tipcalculatorapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputField(modifier: Modifier=Modifier,
               valueState:MutableState<String>,
               labelId: String,
               enabled:Boolean,
               isSingleLine:Boolean,
               keyboardType: KeyboardType = KeyboardType.Number,
               imeAction: ImeAction = ImeAction.Next,
               onActions: KeyboardActions = KeyboardActions.Default
){

    OutlinedTextField(value = valueState.value ,
        onValueChange ={valueState.value = it},
        label = { Text(text = labelId)},
        leadingIcon = {

            Icon(painter = painterResource(id = R.drawable.ic_attach_money) ,
                contentDescription = "Money Icon" )

        },
        singleLine = isSingleLine,
        textStyle = TextStyle(fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onBackground),
        modifier = modifier
            .padding(10.dp)
            .fillMaxWidth(),
        enabled = enabled,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType,
            imeAction = imeAction),
        keyboardActions = onActions,
        shape = RoundedCornerShape(16.dp),
        colors = TextFieldDefaults.textFieldColors(
              containerColor = Color.Transparent,
            textColor = Color.Black,
            focusedIndicatorColor = Color(0xFF826F91),
            focusedLabelColor = Color(0xFF826F91)
        )
       )


}

val iconSizeModifier = Modifier.size(70.dp)

@Composable
fun RoundButton(
    modifier: Modifier = Modifier,
    onClick:() ->Unit,
    background:Color = MaterialTheme.colorScheme.background,
    elevation:Dp = 8.dp,
    imageVector: Painter
){

    Card(modifier = modifier
        .clickable {
                   onClick.invoke()
        }.then(iconSizeModifier),
        shape = CircleShape,
        colors = CardDefaults.cardColors(

            containerColor = background

        ),
        elevation = CardDefaults.cardElevation(

            defaultElevation = elevation
        )) {

        Column(modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {

            Image(painter = imageVector,
                contentDescription = "Circle Image",
                colorFilter = ColorFilter.tint(
                    color = Color.Black
                ))

        }

    }


}