package shared.locations;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Represents the location of a hex on a hex map
 */
public class HexLocation
{
	@SerializedName("x")
	@Expose
	private int x;

    @SerializedName("y")
    @Expose
	private int y;

    public HexLocation() {
    }

	public HexLocation(int x, int y)
	{
		setX(x);
		setY(y);
	}
	
	public int getX()
	{
		return x;
	}
	
	private void setX(int x)
	{
		this.x = x;
	}
	
	public int getY()
	{
		return y;
	}
	
	private void setY(int y)
	{
		this.y = y;
	}
	
	@Override
	public String toString()
	{
		return "HexLocation [x=" + x + ", y=" + y + "]";
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(getClass() != obj.getClass())
			return false;
		HexLocation other = (HexLocation)obj;
		if(x != other.x)
			return false;
        return y == other.y;
    }

    public HexLocation getNeighborLoc(EdgeDirection dir)
	{
		switch (dir)
		{
			case NorthWest:
				return new HexLocation(x - 1, y);
			case North:
				return new HexLocation(x, y - 1);
			case NorthEast:
				return new HexLocation(x + 1, y - 1);
			case SouthWest:
				return new HexLocation(x - 1, y + 1);
			case South:
				return new HexLocation(x, y + 1);
			case SouthEast:
				return new HexLocation(x + 1, y);
			default:
				assert false;
				return null;
		}
	}

    public Set<VertexLocation> getVertices() {
        return Arrays.stream(VertexDirection.values())
                .map(d -> new VertexLocation(this, d))
                .map(VertexLocation::getNormalizedLocation)
                .collect(Collectors.toSet());
    }

    public Set<EdgeLocation> getEdges() {
        return Arrays.stream(EdgeDirection.values())
                .map(d -> new EdgeLocation(this, d))
                .map(EdgeLocation::getNormalizedLocation)
                .collect(Collectors.toSet());
    }

}

