package com.gsm.bee_assistant_android.retrofit.domain.classroom

data class ResponseClassWork(
    val courseWork: List<CourseWork>,
    val message: String?= null
)

data class CourseWork(
    val courseId: String,
    val id: String,
    val title: String,
    val description: String,
    val materials: ArrayList<Materials>,
    val state: String,
    val alternateLink: String,
    val creationTime: String,
    val updateTime: String,
    val dueDate: DueDate,
    val dueTime: DueTime,
    val maxPoints: Int,
    val workType: String,
    val submissionModificationMode: String,
    val creatorUserId: String,
    val topicId: String
)

data class Materials(
    val form: Form,
    val driveFile: DriveFile
)

data class Form(
    val formUrl: String,
    val title: String,
    val thumbnailUrl: String
)

data class DriveFile(
    val driveFile: InnerDriveFile,
    val shareMode: String
)

data class InnerDriveFile(
    val id: String,
    val title: String,
    val alternateLink: String,
    val thumbnailUrl: String
)

data class DueDate(
    val year: Int,
    val month: Int,
    val day: Int
)

data class DueTime(
    val hours: Int,
    val minutes: Int
)