Lilian
======

Lilian is a chatterbot (or a chatterbot algorithm) written in Java which focuses on fast response, given a training corpus.
Lilian has been developed by Martí-Joan Nogué Coll. It started as a "small" project that used around 1000 if clasuses (that's madness huh?) as he was learning his first programming language. Years have passed and Lilian has become a litlle bit more intelligent.

How Lilian works
----------------

The corpus.dat contains a set of questions-answer that Lilian uses as a hint to answer.
Basically what she does is to retrieve the user's question, split it into words and for every word:

	for every word do
		check in corpus all questions that have this word
		probability is 1/number_questions_with_word
		add this answer (if not already) into an array and add the probabilty to a counter variable.
	endfor
	
	Pick answer with highest probability

This algorithm exhaustively iterates through the corpus and so we need a way to access its data as fast as possible. And the answer to that is by using a Double Ternary Search Tree (DTST).
A DTST is a normal TST but where every node has another TST.

### The DTST

