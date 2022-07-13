package wtf.paolovalerdi.amp

import android.app.Application
import com.simplecityapps.ktaglib.KTagLib
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module
import wtf.paolovalerdi.amp.tracks.scanner.Scanner

private val dependencies = module {
    single<SqlDriver> { AndroidSqliteDriver(Database.Schema, get(), "amp.db") }
    single { Database(get()) }
    single { KTagLib() }
    single { Scanner(get(), get(), get()) }
}

class Amp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@Amp)
            modules(dependencies)
        }
    }
}