package com.example.shoppinglistcomposelesson.about_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shoppinglistcomposelesson.R
import com.example.shoppinglistcomposelesson.ui.theme.BlueLight

@Preview(showBackground = true)
@Composable
fun AboutScreen() {
    val uriHandler = LocalUriHandler.current
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier.size(100.dp),
            tint = BlueLight
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "This app developed by N.E.C.O \n" +
                "Version - 1.0.3 \n" +
                "To get more information: \n",
            textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier
                .padding(top = 10.dp)
                .clickable {
                    uriHandler.openUri("https://neco-desarrollo.es")
                }
            ,
            text = ">>> Click here <<<",
            color = BlueLight
        )
    }
}