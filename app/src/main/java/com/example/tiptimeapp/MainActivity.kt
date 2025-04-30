package com.example.tiptimeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tiptimeapp.ui.theme.TipTimeAppTheme
import java.nio.file.WatchEvent
import java.text.NumberFormat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TipTimeAppTheme {
                Surface {
                    TipTimeLayout()
                }
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    TipTimeLayout()
//
//                }
            }
        }
    }
}

@Composable
fun TipTimeLayout() {
    var tipInput by remember { mutableStateOf("") }
    var roundup by remember {mutableStateOf(false)}
    val tipPercentage = tipInput.toDoubleOrNull() ?: 0.0
    var billamount by remember{mutableStateOf("")}
    val actulAmount = billamount.toDoubleOrNull() ?: 0.0
    val tip = calculateTip(actulAmount, tipPercentage, roundup)
    Column(
        modifier = Modifier.fillMaxSize()
            .statusBarsPadding()
            .padding(horizontal = 40.dp)
            .verticalScroll(rememberScrollState())
            .safeDrawingPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.calculate_tip),
            modifier = Modifier
                .padding(bottom = 16.dp, top = 40.dp)
                .align(alignment = Alignment.Start)
        )

        EditNumberField(value = billamount,
            lable = R.string.bill_amount,
            leadingIcon = R.drawable.money_,
            onValueChane = { billamount = it },
            modifier = Modifier.fillMaxSize()
            .padding(bottom = 32.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next))

        EditNumberField(value = tipInput,
            lable = R.string.how_was_the_service,
            leadingIcon = R.drawable.percent,
            onValueChane = { tipInput = it },
            modifier = Modifier.fillMaxSize()
                .padding(bottom = 32.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done))

        RoundTheTipRow(roundup = roundup,
            onRoundupChanged = {roundup = it },
            modifier = Modifier.padding(bottom = 24.dp))
        Text(
            text = stringResource(R.string.tip_amount, tip),
            style = MaterialTheme.typography.displaySmall,
            fontSize = 32.sp)
        Spacer(modifier = Modifier.height(150.dp))
    }
}

private fun calculateTip(amount: Double, tipPercent: Double = 15.0, roundup: Boolean): String {
    var tip = tipPercent / 100 * amount
    if(roundup) {
        tip = kotlin.math.ceil(tip)}
    return NumberFormat.getCurrencyInstance().format(tip)
}

@Composable
fun EditNumberField(
    @StringRes lable : Int,
    @DrawableRes leadingIcon : Int,
    keyboardOptions: KeyboardOptions,
    value : String,
    onValueChane: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {

    // Implementation of EditNumberField
    TextField(
        value = value,
        onValueChange = onValueChane,
        singleLine = true,
        keyboardOptions = keyboardOptions,

        leadingIcon = { Icon(painter = painterResource(leadingIcon), null) },
        label = { Text(stringResource(lable)) },
        modifier = modifier
    )
}

@Composable
fun RoundTheTipRow(modifier: Modifier = Modifier,
                   roundup : Boolean, onRoundupChanged : (Boolean)-> Unit){
    Row(modifier = Modifier.fillMaxWidth().size(48.dp),
        verticalAlignment = Alignment.CenterVertically) {
        Text(text = stringResource(R.string.round_up_tip))
        Switch(modifier = Modifier.fillMaxWidth().
        wrapContentWidth(Alignment.End),
            checked = roundup,
            onCheckedChange = onRoundupChanged)

    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TipTimeAppTheme {
        TipTimeLayout()
    }
}