This project can be used to build a job scheduling system. The responsibility of this project is to schedule the job and execute it. The job and the task to be implemented inside it is to be written by the user.
Following are the APIs configured.
1. /schedule/job - Post mapping:
This will create the job and provide the job ID and job group name. This combination will be used to get job details. This is the expected input format.
   {
   "name": "TimeDelayJob",
   "description": "This job will wait for 45 seconds",
   "triggerNow": false,
   "isRepeating": true,
   "maxRetryAllowed": 0,
   "dateTime": "2025-01-12T20:52:00",
   "timeZone": "Asia/Kolkata",
   "cronExpression": "0 0/30 * ? * * *"
   }
set trigger now if you want to trigger it now. Else provide a future date time or a cron expression for a recurring job. The user will have to write a class with the same name as job name which will have the execution logic. TimeDelayJob has been created as a sample.
2. /scheduledjobs/{jobId}/{jobGroup} - Get mapping:
This is for monitoring the job. This will provide you with the details of the job and it's upcoming and previous executions, with the state of the execution, the trigger ID and trigger group.
This triggerID and trigger group will be useful if we want to cancel this trigger. Then we'll use the following delete mapping to remove that trigger.
3. /triggers/{triggerId}/{triggerGroup} - Delete mapping:
Provide the following details and that particular execution of the trigger will be removed.
4. /jobs/{jobId}/{jobGroup} - Delete mapping:
Provide the following details and that particular job will never be executed
5. /job/trigger/{jobId}/{jobGroup} - Get Mapping:
This api can be called to trigger the job right now. It will trigger at that instant and you can see the output.
6. /job/trigger/{jobId}/{jobGroup}/{dateTime} - Get Mapping:
Use this api to trigger the job at a different time

Besides this, we have provided automatic retry option and also a sample email scheduling logic with all the required inputs. 
Also attaching the sql file which needs to be executed for the quartz framework.