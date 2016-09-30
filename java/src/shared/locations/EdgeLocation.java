package shared.locations;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents the location of an edge on a hex map
 */
public class EdgeLocation
{
	private HexLocation hexLoc;
	private EdgeDirection dir;
	
	public EdgeLocation(HexLocation hexLoc, EdgeDirection dir)
	{
		setHexLoc(hexLoc);
		setDir(dir);
	}
	
	public HexLocation getHexLoc()
	{
		return hexLoc;
	}
	
	private void setHexLoc(HexLocation hexLoc)
	{
		if(hexLoc == null)
		{
			throw new IllegalArgumentException("hexLoc cannot be null");
		}
		this.hexLoc = hexLoc;
	}
	
	public EdgeDirection getDir()
	{
		return dir;
	}
	
	private void setDir(EdgeDirection dir)
	{
		this.dir = dir;
	}
	
	@Override
	public String toString()
	{
		return "EdgeLocation [hexLoc=" + hexLoc + ", dir=" + dir + "]";
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dir == null) ? 0 : dir.hashCode());
		result = prime * result + ((hexLoc == null) ? 0 : hexLoc.hashCode());
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
		EdgeLocation other = (EdgeLocation)obj;
		if(dir != other.dir)
			return false;
		if(hexLoc == null)
		{
			if(other.hexLoc != null)
				return false;
		}
		else if(!hexLoc.equals(other.hexLoc))
			return false;
		return true;
	}
	
	/**
	 * Returns a canonical (i.e., unique) value for this edge location. Since
	 * each edge has two different locations on a map, this method converts a
	 * hex location to a single canonical form. This is useful for using hex
	 * locations as map keys.
	 * 
	 * @return Normalized hex location
	 */
	public EdgeLocation getNormalizedLocation()
	{
		
		// Return an EdgeLocation that has direction NW, N, or NE
		
		switch (dir)
		{
			case NorthWest:
			case North:
			case NorthEast:
				return this;
			case SouthWest:
			case South:
			case SouthEast:
				return new EdgeLocation(hexLoc.getNeighborLoc(dir),
										dir.getOppositeDirection());
			default:
				assert false;
				return null;
		}
	}

    public Set<VertexLocation> getConnectedVertices() {
        EdgeLocation location = getNormalizedLocation();
        Set<VertexLocation> vertices = new HashSet<>();

        HexLocation hex = location.getHexLoc();
        EdgeDirection edgeDirection = location.getDir();

        VertexLocation vertexOne;
        VertexLocation vertexTwo;

        switch (edgeDirection) {
            case North:
                vertexOne = new VertexLocation(hex, VertexDirection.NorthWest);
                vertexTwo = new VertexLocation(hex, VertexDirection.NorthEast);
                vertices.add(vertexOne);
                vertices.add(vertexTwo);
                break;
            case NorthWest:
                vertexOne = new VertexLocation(hex, VertexDirection.West).getNormalizedLocation();
                vertexTwo = new VertexLocation(hex, VertexDirection.NorthWest);
                vertices.add(vertexOne);
                vertices.add(vertexTwo);
                break;
            case NorthEast:
                vertexOne = new VertexLocation(hex, VertexDirection.NorthEast);
                vertexTwo = new VertexLocation(hex, VertexDirection.East).getNormalizedLocation();
                vertices.add(vertexOne);
                vertices.add(vertexTwo);
                break;
            default:
                assert false;
                return null;
        }
        return vertices;
    }
}

