package com.example.tiptimeapp

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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tiptimeapp.ui.theme.TipTimeAppTheme


@Composable
fun TipTimeLayout(viewModel: TipTimeViewModel = viewModel()) {

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

        EditNumberField(value = viewModel.billAmount,
            label = R.string.bill_amount,
            leadingIcon = R.drawable.money_,
            onValueChane = {
                viewModel.billAmount = it
                viewModel.updateTip()},
            modifier = Modifier.fillMaxSize()
                .padding(bottom = 32.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next))

        EditNumberField(value = viewModel.tipInput,
            label = R.string.how_was_the_service,
            leadingIcon = R.drawable.percent,
            onValueChane = {
                viewModel.tipInput = it
                viewModel.updateTip()},
            modifier = Modifier.fillMaxSize()
                .padding(bottom = 32.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done))

        RoundTheTipRow(roundup = viewModel.roundup,
            onRoundupChanged = {
                viewModel.roundup = it
                viewModel.updateTip()},
            modifier = Modifier.padding(bottom = 24.dp))
        Text(
            text = stringResource(R.string.tip_amount, viewModel.tip),
            style = MaterialTheme.typography.displaySmall,
            fontSize = 32.sp)
        Spacer(modifier = Modifier.height(150.dp))
    }
}



@Composable
fun EditNumberField(
    @StringRes label : Int,
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
        label = { Text(stringResource(label)) },
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