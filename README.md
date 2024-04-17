
## Task List Kata
### Credits
The original project cloned from https://github.com/codurance/task-list/tree/master/java

The Task List Kata home page: https://kata-log.rocks/task-list-kata

## Known Issues
The test cases may fail unexpected. When that happens, try to run the tests one more time. 

## Step 2
Create Clean architecture four layers and enrich classes in the entity package

## Step 3
Find more classes in the entity package

## Step 4
Form Commands and Queries in the usecase package

## Step 5
Form Controllers

## Step 6
Form Main Component

## Step 7 
Create Web Controllers

Add Project
curl --data "todolistId=001&projectName=p1"  http://localhost:8080/projects

Add Task
curl --data "todolistId=001&projectName=p1&taskDescription=t1"  http://localhost:8080/tasks

Check
curl --data "todolistId=001&taskId=1&done=true"  http://localhost:8080/setdone

Uncheck
curl --data "todolistId=001&taskId=1&done=false"  http://localhost:8080/setdone

Help
curl http://localhost:8080/help

Show
curl http://localhost:8080/show

## Step 8
Add Persistent Objects