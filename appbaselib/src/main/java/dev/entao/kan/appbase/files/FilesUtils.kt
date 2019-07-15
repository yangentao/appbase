package dev.entao.kan.appbase.files


inline fun <reified T : Any> FileListOf(filename: String): FileList<T> {
    return FileList<T>(filename, ItemCoder(T::class))
}


inline fun <reified T : Any> FileSetOf(filename: String): FileSet<T> {
    return FileSet<T>(filename, ItemCoder(T::class))
}

inline fun <reified T : Any> FileMapOf(filename: String): FileMap<T> {
    return FileMap<T>(filename, ItemCoder(T::class))
}

