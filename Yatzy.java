import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Yatzy {
	
    protected List<Integer> dice;
    
    public Yatzy(int d1, int d2, int d3, int d4, int d5)
    {
        List<Integer> paramsList =  Stream.of(d1, d2, d3, d4, d5).collect(Collectors.toList());
        this.dice =  new ArrayList<Integer>(paramsList);
    }
    
    public static int yatzy(int... dice)
    {
    	Map<Integer, Integer> counts = new HashMap<>(6);
    	
        for (int die : dice) {
        	int value = counts.getOrDefault(die-1, 0) + 1;
        	counts.put(die-1, value);
        }
        
    	List<Integer> res = counts.entrySet().stream()
    			.filter(count -> count.getKey() > 0 && count.getKey() < 6 && count.getValue() == 5)
    			.map(x->x.getValue())
    			.collect(Collectors.toList());
    	
        return res.size() > 0 ? 50 : 0;
    }
    
    public static int chance(int d1, int d2, int d3, int d4, int d5)
    {
        int total = Stream.of(d1, d2, d3, d4, d5)
        		  .mapToInt(Integer::intValue)
        		  .sum();
        return total;
    }

    public static int ones(int d1, int d2, int d3, int d4, int d5) {
        return getSum(Stream.of(d1, d2, d3, d4, d5).collect(Collectors.toList()), 1);

    }

    public static int twos(int d1, int d2, int d3, int d4, int d5) {
        return getSum(Stream.of(d1, d2, d3, d4, d5).collect(Collectors.toList()), 2);
    }

    public static int threes(int d1, int d2, int d3, int d4, int d5) {
        return getSum(Stream.of(d1, d2, d3, d4, d5).collect(Collectors.toList()), 3);
    }
    
    private static Predicate<Integer> isEqualToPadding(int padding) {
        return p -> p == padding;
    }
        
    private static int getSum (List<Integer> list, int padding) {
    	int total = list.stream().filter(isEqualToPadding(padding))
		  .mapToInt(Integer::intValue)
		  .sum();
    	return total;
    }
    

    public int fours()
    {
        return getSum(dice, 4);
    }

    public int fives()
    {
        return getSum(dice, 5);
    }

    public int sixes()
    {
        return getSum(dice, 6);
    }

    public static int score_pair(int d1, int d2, int d3, int d4, int d5)
    {
    	Map<Integer, Integer> counts = new HashMap<>(6);
    	Stream.of(d1, d2, d3, d4, d5).forEach(x -> {
    		int value = counts.getOrDefault(x-1, 0) +1;
    		counts.put(x -1, value);
    	});    	
    	OptionalInt res  = IntStream
        	    .range(0, counts.size())
        	    .filter(i -> counts.getOrDefault(6 - i -1, 0 ) >= 2)
      			.map(i-> (6 - i) * 2 )
      			.findFirst();
    
        return res.isPresent() ? res.getAsInt() : 0;
    }

    public static int two_pair(int d1, int d2, int d3, int d4, int d5)
    {
    	Map<Integer, Integer> counts = new HashMap<>(6);
    	Stream.of(d1, d2, d3, d4, d5).forEach(x -> {
    		int value = counts.getOrDefault(x-1, 0) +1;
    		counts.put(x -1, value);
    	});
    	int[] n = {0};
    	int[] score = {0};
    	IntStream
        	    .range(0, 6)
        	    .filter(i -> counts.getOrDefault(6 - i -1, 0 ) >= 2)
        	    .forEach(i -> { n[0]++;score[0] += (6-i);});
    	return n[0] == 2 ? score[0] * 2 : 0;
    }
    
    private static int x_of_a_kind(Map<Integer, Integer> tallies, int padding) {
    	OptionalInt res  = IntStream
        	    .range(0, 6)
        	    .filter(i -> tallies.getOrDefault( i, 0 ) >= padding)
      			.map(i-> (i+1) * padding )
      			.findFirst();
        return res.isPresent() ? res.getAsInt() : 0;
    }
    
    public static int four_of_a_kind(int d1, int d2, int d3, int d4, int d5)
    {
    	Map<Integer, Integer> tallies = new HashMap<>(6);
    	Stream.of(d1, d2, d3, d4, d5).forEach(x -> {
    		int value = tallies.getOrDefault(x-1, 0) +1;
    		tallies.put(x -1, value);
    	}); 
    	return x_of_a_kind(tallies, 4);
    }

    public static int three_of_a_kind(int d1, int d2, int d3, int d4, int d5)
    {
    	Map<Integer, Integer> tallies = new HashMap<>(6);
    	Stream.of(d1, d2, d3, d4, d5).forEach(x -> {
    		int value = tallies.getOrDefault(x-1, 0) +1;
    		tallies.put(x -1, value);
    	}); 
    	return x_of_a_kind(tallies, 3);
    }

    public static int smallStraight(int d1, int d2, int d3, int d4, int d5)
    {
    	Map<Integer, Integer> tallies = new HashMap<>(6);
    	Stream.of(d1, d2, d3, d4, d5).forEach(x -> {
    		int value = tallies.getOrDefault(x-1, 0) +1;
    		tallies.put(x -1, value);
    	});
    	List<Integer> res = tallies.entrySet().stream()
    			.filter(count -> count.getKey() >= 0 && count.getKey() < 5 && count.getValue() == 1)
    			.map(x->x.getValue())
    			.collect(Collectors.toList());
    	
        return res.size() == 5 ? 15 : 0;
    }

    public static int largeStraight(int d1, int d2, int d3, int d4, int d5)
    {
    	Map<Integer, Integer> tallies = new HashMap<>(6);
    	Stream.of(d1, d2, d3, d4, d5).forEach(x -> {
    		int value = tallies.getOrDefault(x-1, 0) +1;
    		tallies.put(x -1, value);
    	});
    	List<Integer> res = tallies.entrySet().stream()
    			.filter(count -> count.getKey() >= 1 && count.getKey() < 6 && count.getValue() == 1)
    			.map(x->x.getValue())
    			.collect(Collectors.toList());
    	
        return res.size() == 5 ? 20 : 0;
    }

    public static int fullHouse(int d1, int d2, int d3, int d4, int d5)
    {
    	Map<Integer, Integer> tallies = new HashMap<>(6);
        boolean[] _2 = {false};
        int[] _2_at = {0};
        boolean[] _3 = {false};
        int[] _3_at = {0};
        
    	Stream.of(d1, d2, d3, d4, d5).forEach(x -> {
    		int value = tallies.getOrDefault(x-1, 0) +1;
    		tallies.put(x -1, value);
    	});
    	
    	IntStream
	    .range(0, 6)
	    .filter(i -> tallies.getOrDefault(i, 0 ) == 2 || tallies.getOrDefault(i, 0 ) == 3)
	    .forEach(i -> {
	    	_2[0] = tallies.getOrDefault(i, 0 ) == 2 ? true : _2[0];
	    	_3[0] = tallies.getOrDefault(i, 0 ) == 3 ? true : _3[0];
	    	_2_at[0] =  tallies.getOrDefault(i, 0 ) == 2 ?  i+1 : _2_at[0] ;
	    	_3_at[0] = tallies.getOrDefault(i, 0 ) == 3 ?  i+1 : _3_at[0] ;
	    });
        return  (_2[0] && _3[0]) ?  _2_at[0] * 2 + _3_at[0] * 3 : 0;
    }
}