# Pre-work - *Simple ToDo*

**Simple ToDo** is an android app that allows building a todo list and basic todo items management functionality including adding new items, editing and deleting an existing item.

Submitted by: **Divya Yadav**

Time spent: **~9 hours** hours spent in total

## User Stories

The following **required** functionality is completed:

* [x] User can **successfully add and remove items** from the todo list
* [x] User can **tap a todo item in the list and bring up an edit screen for the todo item** and then have any changes to the text reflected in the todo list.
* [x] User can **persist todo items** and retrieve them properly on app restart

The following **optional** features are implemented:

* [x] Persist the todo items [into SQLite](http://guides.codepath.com/android/Persisting-Data-to-the-Device#sqlite) instead of a text file
* [x] Improve style of the todo items in the list [using a custom adapter](http://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView)
* [x] Add support for completion due dates for todo items (and display within listview item)
* [x] Use a [DialogFragment](http://guides.codepath.com/android/Using-DialogFragment) instead of new Activity for editing items
* [x] Add support for selecting the priority of each todo item (and display in listview item)
* [x] Tweak the style improving the UI / UX, play with colors, images or backgrounds

The following **additional** features are implemented:

* [x] Show notification every 12 hours only for overdue tasks
* [x] Display priority of todo item as image in listview & add tasks to the top of the list
* [x] Use of SwipeActionAdapter to give swipe option to the user to delete the task on right swipe and chage status from to incomplete/complete on left swipe
* [x] Dialog Fragment has a checkbox to change the status of the tasks

**New changes after submission**
* [x] Removes Floating action button and use actionbar menu
* [x] UI Theme/color/icon changes
* [x] Add **Text to speech** for quick addition of task


## Video Walkthrough 

Here's a walkthrough of implemented user stories:

<img src='https://github.com/divyayadav1606/SimpleToDo/blob/master/Simpletodo.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

Here is a screenshot of the notification:

<img src='https://github.com/divyayadav1606/SimpleToDo/blob/master/Screenshot.png' width='' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).

##Notes
Working on giving a different color to the strikethrough on the completed tasks, but have not been successful yet.

## License

    Copyright [2017] [Divya Yadav]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
