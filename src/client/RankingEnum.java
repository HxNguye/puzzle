package client;


/**
 * Enum class for the ranking system
 */
public enum RankingEnum 
{
	S("Impressive"), 
	A("You're pretty good!"), 
	B("Almost gets you almost"), 
	C("You are Meh!"), 
	D("Total FAIL!"), 
	F("You are terrible!");
	

	private String name;
	
	private RankingEnum(String s)
	{
		name = s;
	}
	public boolean equalsName(String otherName)
	{
		return (otherName == null) ? false : name.equals(otherName);
	}
	
    public String toString() 
    {
        return this.name;
    }
	
}
