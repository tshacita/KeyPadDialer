package keypaddialer.di

import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import keypaddialer.di.module.GsonBuilder
import keypaddialer.di.module.OkHttpBuilder
import keypaddialer.feature.keypad.data.datasource.KeypadDatasource
import keypaddialer.feature.keypad.data.datasource.KeypadRemoteDatasource
import keypaddialer.feature.keypad.data.repository.KeypadRepository
import keypaddialer.feature.keypad.domain.usecase.KeypadInterface
import keypaddialer.feature.keypad.domain.usecase.KeypadUseCase
import keypaddialer.feature.keypad.presentation.KeypadViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::KeypadViewModel)
}

val networkModule = module {
    single<ChuckerInterceptor> {
        ChuckerInterceptor.Builder(get())
            .collector(
                ChuckerCollector(
                    get(),
                    showNotification = true,
                    retentionPeriod = RetentionManager.Period.ONE_HOUR
                )
            )
            .build()
    }

    single {
        OkHttpBuilder(
            context = androidContext(),
            chuckerInterceptor = get()
        ).build()
    }

    single {
        GsonBuilder().build()
    }
}

val datasourceModule = module {
    factory <KeypadDatasource> {
        KeypadRemoteDatasource(okHttpClient = get(), gson = get())
    }
}

val useCaseModule = module {
    factory{ KeypadUseCase(get()) }
}

val repositoryModule = module {
    single { KeypadRepository(get()) }
}

val appModules = listOf(
    networkModule,
    datasourceModule,
    useCaseModule,
    repositoryModule,
    viewModelModule
)