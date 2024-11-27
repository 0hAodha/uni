## Question 1
- Term suggestion: suggest terms that split the query space.
- E.g., `jaguar`: add the word `car` or the word `cat`.
- Don't focus on adding similar terms -- limited utility.
- Want to suggest a diverse number of terms.
- We want to suggest terms that are maximally dissimilar to each other while still be similar to the original query.
- Trade-off: could maximise diversity by picking random terms, but these would not be relevant to the query.
- Want to suggest terms that make a more specific query.

## Question 2
- Term-term correlation: know co-occurrence of terms, e.g., t1 tends to occur with t2.
- User-user correlation: now that some users are making similar queries.
    - Could suggest terms that similar searchers are using that maybe they haven't.
- Ignoring temporal evidence.
- Multiple term suggestions in ranked order preferable.
- Consider relation to autofill in Google.
- Impossible to get fully right, looking for identification of data being used and a valid approach to using it, plus advantages & disadvantages of this approach.

