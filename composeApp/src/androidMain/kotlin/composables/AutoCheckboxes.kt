//package composables
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.material3.CheckboxDefaults
//import androidx.compose.material3.Text
//import androidx.compose.material3.TriStateCheckbox
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.MutableState
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.state.ToggleableState
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import nodes.*
//
//@Composable
//fun AutoCheckboxesHorizontal(flippingAuto: MutableState<Boolean>) {
//    Column(modifier = Modifier.fillMaxWidth()) {
//        Column(Modifier.align(Alignment.CenterHorizontally)) {
//            val color = CheckboxDefaults.colors(
//                checkedColor = Color.Cyan,
//                checkmarkColor = Color.Black
//            )
//
//            fun getNewState(state: ToggleableState) = when (state) {
//                ToggleableState.Off -> ToggleableState.Indeterminate
//                ToggleableState.Indeterminate -> ToggleableState.On
//                ToggleableState.On -> ToggleableState.Off
//            }
//            if (flippingAuto.value) {
//                Row {
//                    Spacer(Modifier.width(10.dp))
//                    Text("c", Modifier.align(Alignment.CenterVertically))
//                    TriStateCheckbox(
//                        state = f1.value,
//                        onClick = {
//                            f1.value = getNewState(f1.value)
//                        },
//                        colors = color
//                    )
//                    Text("b", Modifier.align(Alignment.CenterVertically))
//                    TriStateCheckbox(
//                        state = f2.value,
//                        onClick = {
//                            f2.value = getNewState(f2.value)
//                        },
//                        colors = color
//                    )
//                    Text("a", Modifier.align(Alignment.CenterVertically))
//                    TriStateCheckbox(
//                        state = f3.value,
//                        onClick = {
//                            f3.value = getNewState(f3.value)
//                        },
//                        colors = color
//                    )
//                    Text(
//                        "Auto Collect",
//                        Modifier.align(Alignment.CenterVertically),
//                        fontSize = 20.sp
//                    )
//                }
//                Row {
//                    Spacer(Modifier.width(10.dp))
//                    Text(5.toString(), Modifier.align(Alignment.CenterVertically))
//                    TriStateCheckbox(
//                        state = m1.value,
//                        onClick = {
//                            m1.value = getNewState(m1.value)
//                        },
//                        colors = color
//                    )
//                    Text(4.toString(), Modifier.align(Alignment.CenterVertically))
//                    TriStateCheckbox(
//                        state = m2.value,
//                        onClick = {
//                            m2.value = getNewState(m2.value)
//                        },
//                        colors = color
//                    )
//                    Text(3.toString(), Modifier.align(Alignment.CenterVertically))
//                    TriStateCheckbox(
//                        state = m3.value,
//                        onClick = {
//                            m3.value = getNewState(m3.value)
//                        },
//                        colors = color
//                    )
//                    Text(2.toString(), Modifier.align(Alignment.CenterVertically))
//                    TriStateCheckbox(
//                        state = m4.value,
//                        onClick = {
//                            m4.value = getNewState(m4.value)
//                        },
//                        colors = color
//                    )
//                    Text(1.toString(), Modifier.align(Alignment.CenterVertically))
//                    TriStateCheckbox(
//                        state = m5.value,
//                        onClick = {
//                            m5.value = getNewState(m5.value)
//                        },
//                        colors = color
//                    )
//                }
//            }else{
//                Row {
//                    Text(
//                        "Auto Collect",
//                        Modifier.align(Alignment.CenterVertically),
//                        fontSize = 20.sp,
//                        color = Color.White
//                    )
//                    Spacer(Modifier.width(10.dp))
//                    Text("a",color = Color.White, modifier = Modifier.align(Alignment.CenterVertically))
//                    TriStateCheckbox(
//                        state = f3.value,
//                        onClick = {
//                            f3.value = getNewState(f3.value) },
//                        colors = color
//                    )
//                    Text("b",color = Color.White, modifier = Modifier.align(Alignment.CenterVertically))
//                    TriStateCheckbox(
//                        state = f2.value,
//                        onClick = {
//                            f2.value = getNewState(f2.value) },
//                        colors = color
//                    )
//                    Text("c",color = Color.White, modifier = Modifier.align(Alignment.CenterVertically))
//                    TriStateCheckbox(
//                        state = f1.value,
//                        onClick = {
//                            f1.value = getNewState(f1.value) },
//                        colors = color
//                    )
//                }
//                Row{
//                    Spacer(Modifier.width(10.dp))
//                    Text(1.toString(),color = Color.White, modifier = Modifier.align(Alignment.CenterVertically))
//                    TriStateCheckbox(
//                        state = m5.value,
//                        onClick = {
//                            m5.value = getNewState(m5.value) },
//                        colors = color
//                    )
//                    Text(2.toString(),color = Color.White, modifier = Modifier.align(Alignment.CenterVertically))
//                    TriStateCheckbox(
//                        state = m4.value,
//                        onClick = {
//                            m4.value = getNewState(m4.value) },
//                        colors = color
//                    )
//                    Text(3.toString(),color = Color.White, modifier = Modifier.align(Alignment.CenterVertically))
//                    TriStateCheckbox(
//                        state = m3.value,
//                        onClick = {
//                            m3.value = getNewState(m3.value) },
//                        colors = color
//                    )
//                    Text(4.toString(),color = Color.White, modifier = Modifier.align(Alignment.CenterVertically))
//                    TriStateCheckbox(
//                        state = m2.value,
//                        onClick = {
//                            m2.value = getNewState(m2.value) },
//                        colors = color
//                    )
//                    Text(5.toString(),color = Color.White, modifier = Modifier.align(Alignment.CenterVertically))
//                    TriStateCheckbox(
//                        state = m1.value,
//                        onClick = {
//                            m1.value = getNewState(m1.value) },
//                        colors = color
//                    )
//                }
//            }
//        }
//    }
//}
//
//
//
//
//@Composable
//fun AutoCheckboxesVertical(
//    flippingAuto: MutableState<Boolean>
//) {
//
//        Column(modifier = Modifier.fillMaxWidth()) {
//            Text(
//                "Auto Collect",
//                Modifier.align(Alignment.CenterHorizontally),
//                fontSize = 20.sp
//            )
//            Spacer(Modifier.width(10.dp))
//            Row(Modifier.align(Alignment.CenterHorizontally)) {
//                val color = CheckboxDefaults.colors(
//                    checkedColor = Color.Cyan,
//                    checkmarkColor = Color.Black
//                )
//
//                fun getNewState(state: ToggleableState) = when (state) {
//                    ToggleableState.Off -> ToggleableState.Indeterminate
//                    ToggleableState.Indeterminate -> ToggleableState.On
//                    ToggleableState.On -> ToggleableState.Off
//                }
//                if(flippingAuto.value) {
//                Column {
//
//                    Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
//                        Text("c", Modifier.align(Alignment.CenterVertically))
//                        TriStateCheckbox(
//                            state = f1.value,
//                            onClick = {
//                                f1.value = getNewState(f1.value)
//                            },
//                            colors = color
//                        )
//                    }
//                    Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
//                        Text("b", Modifier.align(Alignment.CenterVertically))
//                        TriStateCheckbox(
//                            state = f2.value,
//                            onClick = {
//                                f2.value = getNewState(f2.value)
//                            },
//                            colors = color
//                        )
//                    }
//                    Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
//                        Text("a", Modifier.align(Alignment.CenterVertically))
//                        TriStateCheckbox(
//                            state = f3.value,
//                            onClick = {
//                                f3.value = getNewState(f3.value)
//                            },
//                            colors = color
//                        )
//                    }
//                }
//                Spacer(Modifier.width(15.dp))
//                Column {
//                    Spacer(Modifier.width(10.dp))
//                    Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
//                        Text(5.toString(), Modifier.align(Alignment.CenterVertically))
//                        TriStateCheckbox(
//                            state = m1.value,
//                            onClick = {
//                                m1.value = getNewState(m1.value)
//                            },
//                            colors = color
//                        )
//                    }
//                    Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
//                        Text(4.toString(), Modifier.align(Alignment.CenterVertically))
//                        TriStateCheckbox(
//                            state = m2.value,
//                            onClick = {
//                                m2.value = getNewState(m2.value)
//                            },
//                            colors = color
//                        )
//                    }
//                    Row {
//                        Text(3.toString(), Modifier.align(Alignment.CenterVertically))
//                        TriStateCheckbox(
//                            state = m3.value,
//                            onClick = {
//                                m3.value = getNewState(m3.value)
//                            },
//                            colors = color
//                        )
//                    }
//                    Row {
//                        Text(2.toString(), Modifier.align(Alignment.CenterVertically))
//                        TriStateCheckbox(
//                            state = m4.value,
//                            onClick = {
//                                m4.value = getNewState(m4.value)
//                            },
//                            colors = color
//                        )
//                    }
//                    Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
//                        Text(1.toString(), Modifier.align(Alignment.CenterVertically))
//                        TriStateCheckbox(
//                            state = m5.value,
//                            onClick = {
//                                m5.value = getNewState(m5.value)
//                            },
//                            colors = color
//                        )
//                    }
//                }
//            }else{
//                    Column {
//                        Column {
//                            Spacer(Modifier.width(10.dp))
//                            Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
//                                Text(5.toString(), Modifier.align(Alignment.CenterVertically))
//                                TriStateCheckbox(
//                                    state = m1.value,
//                                    onClick = {
//                                        m1.value = getNewState(m1.value)
//                                    },
//                                    colors = color
//                                )
//                            }
//                            Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
//                                Text(4.toString(), Modifier.align(Alignment.CenterVertically))
//                                TriStateCheckbox(
//                                    state = m2.value,
//                                    onClick = {
//                                        m2.value = getNewState(m2.value)
//                                    },
//                                    colors = color
//                                )
//                            }
//                            Row {
//                                Text(3.toString(), Modifier.align(Alignment.CenterVertically))
//                                TriStateCheckbox(
//                                    state = m3.value,
//                                    onClick = {
//                                        m3.value = getNewState(m3.value)
//                                    },
//                                    colors = color
//                                )
//                            }
//                            Row {
//                                Text(2.toString(), Modifier.align(Alignment.CenterVertically))
//                                TriStateCheckbox(
//                                    state = m4.value,
//                                    onClick = {
//                                        m4.value = getNewState(m4.value)
//                                    },
//                                    colors = color
//                                )
//                            }
//                            Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
//                                Text(1.toString(), Modifier.align(Alignment.CenterVertically))
//                                TriStateCheckbox(
//                                    state = m5.value,
//                                    onClick = {
//                                        m5.value = getNewState(m5.value)
//                                    },
//                                    colors = color
//                                )
//                            }
//                        }
//                    }
//                    Spacer(Modifier.width(15.dp))
//                    Column {
//                        Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
//                            Text("c", Modifier.align(Alignment.CenterVertically))
//                            TriStateCheckbox(
//                                state = f1.value,
//                                onClick = {
//                                    f1.value = getNewState(f1.value)
//                                },
//                                colors = color
//                            )
//                        }
//                        Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
//                            Text("b", Modifier.align(Alignment.CenterVertically))
//                            TriStateCheckbox(
//                                state = f2.value,
//                                onClick = {
//                                    f2.value = getNewState(f2.value)
//                                },
//                                colors = color
//                            )
//                        }
//                        Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
//                            Text("a", Modifier.align(Alignment.CenterVertically))
//                            TriStateCheckbox(
//                                state = f3.value,
//                                onClick = {
//                                    f3.value = getNewState(f3.value)
//                                },
//                                colors = color
//                            )
//                        }
//                    }
//            }
//        }
//    }
//}
