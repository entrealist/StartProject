package com.jeff.startproject.di

import com.jeff.startproject.view.db.DbViewModel
import com.jeff.startproject.view.edittext.EditTextViewModel
import com.jeff.startproject.view.eventbus.EventBusViewModel
import com.jeff.startproject.view.file.FileContentViewModel
import com.jeff.startproject.view.file.FileMenuViewModel
import com.jeff.startproject.view.flowcontrol.FlowControlViewModel
import com.jeff.startproject.view.login.LoginViewModel
import com.jeff.startproject.view.main.MainViewModel
import com.jeff.startproject.view.navigation.NavigationViewModel
import com.jeff.startproject.view.sample.SampleViewModel
import com.jeff.startproject.view.table.TableViewModel
import com.jeff.startproject.view.websocket.WebSocketViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModules = module {
    viewModel { MainViewModel() }
    viewModel { EventBusViewModel() }
    viewModel { TableViewModel() }
    viewModel { DbViewModel() }
    viewModel { FlowControlViewModel() }
    viewModel { SampleViewModel() }
    viewModel { LoginViewModel() }
    viewModel { WebSocketViewModel() }
    viewModel { NavigationViewModel() }
    viewModel { EditTextViewModel() }
    viewModel { FileMenuViewModel() }
    viewModel { FileContentViewModel() }
}