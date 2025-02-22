#!/bin/sh

file="results.tsv"
printf "timestamp\tremote\trefid\tst\tt\twhen\tpoll\treach\tdelay\toffset\tjitter\n" > "$file"  # add headings to results file

# every 20 minutes for 8 hours
for i in $(seq 1 24); do
    # add the ntpq peers data in TSV format to file with timestamp prepended
    ntpq --peers | awk '
        (NR > 2 ) {
            timestamp = systime();
            gsub(/ +/, "\t");
            print(timestamp "\t" $0);
        }
    ' | tee "$file"

    # do nothing for 20 minutes
    sleep 20m
done
