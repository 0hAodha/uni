clear;

% 1. read in the file and confirm the number of records (336,766)
flights = readtable("Flights.csv"); 
numRecords = height(flights)

% 2. convert "origin" & "dest" to strings (from cell type) 
flights.origin = string(flights.origin); 
flights.dest = string(flights.dest); 


% 3. check the number of missing values for the departure time 
numMissing = sum(ismissing(flights.dep_delay))

% 4. filter all the missing values from the departure delay and check the
% difference in the number of records
flights_clean = flights(~isnan(flights.dep_delay), :);
numRecordsClean = height(flights_clean);
disp("Number of records in flights = " + numRecords); 
disp("Number of records in flights_clean = " + numRecordsClean);

% 5. confirm the difference in records between the two tables 
diff = numRecords - numRecordsClean

% 6. Remove any departure delay greater than 2 hours (120 minutes). This 
% leaves 318,798 observations. 
flights_final = flights_clean(flights_clean.dep_delay <= 120, :); 
height(flights_final)

% 7. Generate the following table and graph, showing the average delay per 
% month. 
months = unique(flights_final.Month);

res1 = table(months, zeros(size(months)), 'VariableNames', {'Month', 'AvgDelayMonth'});
for i = months(1):length(months)
    month_delays = flights_final.dep_delay(flights_final.Month == i);
    avg_delay_month = mean(month_delays); 
    res1.AvgDelayMonth(i) = avg_delay_month;
end
res1
plot(res1.Month, res1.AvgDelayMonth, '-o');
title('Average Delay by Month');

% 8. Generate the following table and graph, showing the average delay per
% hour.
hours = transpose(1:24);

res2 = table(hours, zeros(size(hours)), 'VariableNames', {'Hour', 'AvgDelayHour'});
for i = hours(1):length(hours)
    hour_delays = flights_final.dep_delay(flights_final.hour == i);
    avg_delay_hour = mean(hour_delays); 
    res2.AvgDelayHour(i) = avg_delay_hour;
end
res2 = res2(~isnan(res2.AvgDelayHour),:);
res2
plot(res2.Hour, res2.AvgDelayHour, '-o');
title('Average Delay by Hour of the Day');

% 9. Generate the following table and graph, showing the average delay by
% month and by origin 
res3 = renamevars(removevars(groupsummary(flights_final,["Month","origin"],"mean","dep_delay"),'GroupCount'), 'mean_dep_delay', 'AvrDelayMonthOrigin')

jfk = res3(res3.origin == 'JFK', {'Month', 'AvrDelayMonthOrigin'});
subplot(3,1,1);
plot(jfk.Month, jfk.AvrDelayMonthOrigin, '-o');
title("JFK");

ewr = res3(res3.origin == 'EWR', {'Month', 'AvrDelayMonthOrigin'});
subplot(3,1,2);
plot(ewr.Month, ewr.AvrDelayMonthOrigin, '-o');
title("EWR");

lga = res3(res3.origin == 'LGA', {'Month', 'AvrDelayMonthOrigin'});
subplot(3,1,3);
plot(lga.Month, lga.AvrDelayMonthOrigin, '-o');
title("LGA");
