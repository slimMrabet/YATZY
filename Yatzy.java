import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
    	List<Integer> valueList = counts.values().stream().collect(Collectors.toList()); 
    	
    	OptionalInt res  = IntStream
        	    .range(0, valueList.size())
        	    .filter(i -> counts.getOrDefault(6 - i -1, 0 ) >= 2)
      			.map(i-> (6 - i) * 2 )
      			.findFirst();
    
        return res.isPresent() ? res.getAsInt() : 0;
    }

    public static int two_pair(int d1, int d2, int d3, int d4, int d5)
    {
        int[] counts = new int[6];
        counts[d1-1]++;
        counts[d2-1]++;
        counts[d3-1]++;
        counts[d4-1]++;
        counts[d5-1]++;
        int n = 0;
        int score = 0;
        for (int i = 0; i < 6; i += 1)
            if (counts[6-i-1] >= 2) {
                n++;
                score += (6-i);
            }        
        if (n == 2)
            return score * 2;
        else
            return 0;
    }

    public static int four_of_a_kind(int _1, int _2, int d3, int d4, int d5)
    {
        int[] tallies;
        tallies = new int[6];
        tallies[_1-1]++;
        tallies[_2-1]++;
        tallies[d3-1]++;
        tallies[d4-1]++;
        tallies[d5-1]++;
        for (int i = 0; i < 6; i++)
            if (tallies[i] >= 4)
                return (i+1) * 4;
        return 0;
    }

    public static int three_of_a_kind(int d1, int d2, int d3, int d4, int d5)
    {
        int[] t;
        t = new int[6];
        t[d1-1]++;
        t[d2-1]++;
        t[d3-1]++;
        t[d4-1]++;
        t[d5-1]++;
        for (int i = 0; i < 6; i++)
            if (t[i] >= 3)
                return (i+1) * 3;
        return 0;
    }

    public static int smallStraight(int d1, int d2, int d3, int d4, int d5)
    {
        int[] tallies;
        tallies = new int[6];
        tallies[d1-1] += 1;
        tallies[d2-1] += 1;
        tallies[d3-1] += 1;
        tallies[d4-1] += 1;
        tallies[d5-1] += 1;
        if (tallies[0] == 1 &&
            tallies[1] == 1 &&
            tallies[2] == 1 &&
            tallies[3] == 1 &&
            tallies[4] == 1)
            return 15;
        return 0;
    }

    public static int largeStraight(int d1, int d2, int d3, int d4, int d5)
    {
        int[] tallies;
        tallies = new int[6];
        tallies[d1-1] += 1;
        tallies[d2-1] += 1;
        tallies[d3-1] += 1;
        tallies[d4-1] += 1;
        tallies[d5-1] += 1;
        if (tallies[1] == 1 &&
            tallies[2] == 1 &&
            tallies[3] == 1 &&
            tallies[4] == 1
            && tallies[5] == 1)
            return 20;
        return 0;
    }

    public static int fullHouse(int d1, int d2, int d3, int d4, int d5)
    {
        int[] tallies;
        boolean _2 = false;
        int i;
        int _2_at = 0;
        boolean _3 = false;
        int _3_at = 0;
        tallies = new int[6];
        tallies[d1-1] += 1;
        tallies[d2-1] += 1;
        tallies[d3-1] += 1;
        tallies[d4-1] += 1;
        tallies[d5-1] += 1;

        for (i = 0; i != 6; i += 1)
            if (tallies[i] == 2) {
                _2 = true;
                _2_at = i+1;
            }

        for (i = 0; i != 6; i += 1)
            if (tallies[i] == 3) {
                _3 = true;
                _3_at = i+1;
            }

        if (_2 && _3)
            return _2_at * 2 + _3_at * 3;
        else
            return 0;
    }
}