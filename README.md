# Keypad Dialer
KeyPad Dialer is a simple native Android application built with Kotlin. It allows users to input, 
delete, and validate dialer inputs. The project is structured using the MVVM (Model-View-ViewModel) architecture pattern, 
ensuring a clean separation of concerns and making the app scalable and easy to maintain. 
The app also leverages Koin for dependency injection and Navigation for managing the navigation flow between screens.

# Features
 - Input: Allows users to input digits (0-9) , * and # into the keypad.
 - Delete: Supports deleting individual digits (0-9), * and # from the input.
 - Validation: Includes basic validation logic to check if the input follows specific rules (e.g., valid digit (0-9), * and #).

# Architecture
The app follows the MVVM (Model-View-ViewModel) architecture pattern, which provides a clear separation of concerns:
 - Model: Represents the data layer, including the repository pattern for accessing data.
 - ViewModel: Handles business logic and communicates between the Model and the View. It ensures that the View remains simple and focuses on displaying data.
 - View: The UI layer that observes changes in the ViewModel and updates the UI accordingly.

# Libraries & Tools Used
 - Kotlin: The primary language used to build the app.
 - MVVM Architecture: Helps maintain a clear separation of concerns and encourages a more testable, maintainable codebase.
 - Koin: A lightweight dependency injection framework for Kotlin.
 - Navigation: Jetpack Navigation component for managing fragment-based navigation within the app.

# Getting Started

## Prerequisites
 - Android Studio: Ensure that Android Studio is installed on your system.

## Clone the repository
To get a local copy of the project, use the following command:
```Command
git clone https://github.com/tshacita/KeyPadDialer.git
```

## Running the Project
1. Open the project in Android Studio.
2. Sync the project with Gradle.
3. Select keypadDebug(Default) variant in the project.

# Dependency Injection with Koin

Koin is used for dependency injection to manage the dependencies within the app. Here's how it's set up:
- In the app_module.kt, we define the dependencies for the app (such as the ViewModels and repositories).
- The app initializes Koin in the Application class.

## Example of Koin setup in app_module.kt:
```kotlin app_module.kt
class KeyPadApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        try {
            startKoin {
                androidContext(this@KeyPadApplication)
                modules(modules = appModules)
                androidLogger()
            }
            Log.d("KeyPadApplication", "Koin started successfully")
        } catch (e: Exception) {
            Log.e("KeyPadApplication", "Koin initialization failed", e)
        }
    }
}
```

# Navigation

The app uses the Jetpack Navigation component to handle fragment-based navigation. This makes the app's navigation flow easy to manage and scalable.

```xml nav_keypad_graph.xml
<navigation xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/nav_graph"
    app:startDestination="@id/KeyPadFragment">

    <fragment
        android:id="@+id/KeyPadFragment"
        tools:layout="@layout/fragment_key_pad"
        android:label="@string/app_name"
        android:name="keypaddialer.feature.keypad.presentation.KeypadFragment"/>

</navigation>
```

# Validation Logic
The app includes basic input validation to ensure that the user-entered data is in the correct format. For example, the input might need to meet certain criteria such as being within a specific length or containing only valid characters (numbers).

## Example validation logic in the UseCaee:
```kotlin KeypadViewUseCase.kt
private val _item = MutableStateFlow("")
val item: Flow<String> = _item

override fun deleteLastDigit() {
    _item.value = _item.value.dropLast( 1)
}

override fun deleteCursorPosition(range: IntRange) {
    _item.value = _item.value.removeRange(range = range)
}

override fun validates(text: String?): Boolean {
    val result = repository.getNumPad()
    if (result is Result.Success) {
        val item = result.data
        text?.let {
            for (t in text) {
                if (item?.contains(t.toString()) != true) {
                    return false
                }
            }
            return true
        }
        return false
    } else return false
}

override fun inputField(text: String) {
    _item.value = text
}
```