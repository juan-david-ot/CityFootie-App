package com.example.cityfootie_compose.ui.screens.modify

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cityfootie_compose.ui.components.data_field.DataField
import com.example.cityfootie_compose.util.toFloat

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ModifyScreen(
    goBack: () -> Unit,
    email: String,
    modifyViewModel: ModifyViewModel = hiltViewModel()
) {
    var isCompleted = modifyViewModel.isCompleted
    Scaffold(topBar = {
        TopAppBar {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Arrow Back",
                modifier = Modifier.clickable {
                    isCompleted = false
                    goBack()
                })
        }
    }) {
        BodyContent(goBack, email, isCompleted)
    }
}

@Composable
fun BodyContent(
    goBack: () -> Unit,
    email: String,
    isCompleted: Boolean,
    modifyViewModel: ModifyViewModel = hiltViewModel()
) {
    val player = modifyViewModel.player

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    val response = modifyViewModel.response
    val isModifyCompleted = modifyViewModel.isModifyCompleted
    val isError: Boolean = modifyViewModel.isError

    val isButtonEnabled: Boolean by modifyViewModel.isButtonEnabled.observeAsState(initial = false)

    if (!isCompleted) {
        LaunchedEffect(Unit) {
            modifyViewModel.getPlayer(email)
        }
    }

    if (isCompleted) {
        if (player != null) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(40.dp))

                Text(
                    text = "Modificación de Datos",
                    fontSize = 35.sp,
                    color = MaterialTheme.colors.primary,
                    style = MaterialTheme.typography.h1,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif
                )
            }

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                DataField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp)
                        .padding(top = 24.dp)
                        .focusRequester(focusRequester),
                    label = "Username",
                    placeholder = "Username",
                    text = modifyViewModel.username,
                    imeAction = ImeAction.Next,
                    isEnabled = true,
                    keyBoardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    ),
                    keyboardType = KeyboardType.Text,
                    onChange = { modifyViewModel.onUsernameChange(it) }
                )

                //NOMBRE
                DataField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp)
                        .padding(top = 0.dp)
                        .focusRequester(focusRequester),
                    label = "Nombre",
                    placeholder = "Nombre",
                    text = modifyViewModel.name,
                    imeAction = ImeAction.Next,
                    isEnabled = true,
                    keyBoardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    ),
                    keyboardType = KeyboardType.Text,
                    onChange = { modifyViewModel.onNameChange(it) }
                )

                //APELLIDOS
                DataField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp)
                        .padding(top = 0.dp)
                        .focusRequester(focusRequester),
                    label = "Apellidos",
                    placeholder = "Apellidos",
                    text = modifyViewModel.surnames,
                    imeAction = ImeAction.Next,
                    isEnabled = true,
                    keyBoardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    ),
                    keyboardType = KeyboardType.Text,
                    onChange = { modifyViewModel.onSurnameChange(it) }
                )

                //DORSAL
                DataField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp)
                        .padding(top = 0.dp)
                        .focusRequester(focusRequester),
                    label = "Dorsal",
                    placeholder = "Dorsal",
                    text = modifyViewModel.number,
                    imeAction = ImeAction.Next,
                    isEnabled = true,
                    keyBoardActions = KeyboardActions(
                        onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }
                    ),
                    keyboardType = KeyboardType.Number,
                    onChange = { modifyViewModel.onNumberChange(it) }
                )

                LaunchedEffect(response) {
                    if (response != null) {
                        if (isModifyCompleted) {
                            goBack()
                        }
                    }
                }

                Text(
                    text = "Nombre de usuario ya existente",
                    modifier = Modifier.alpha(isError.toFloat()),
                    color = MaterialTheme.colors.error
                )

                Row {
                    Button(
                        onClick = {
                            modifyViewModel.updatePlayer(email)
                        },
                        enabled = isButtonEnabled
                    ) {
                        Text(
                            text = "Modificar",
                            fontSize = 20.sp
                        )
                    }
                }
            }
        }
    }
    val isLoading: Boolean = modifyViewModel.isLoading
    if (isLoading) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator()
        }
    }
}