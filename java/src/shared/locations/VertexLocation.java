package shared.locations;

import client.game.GameManager;
import shared.models.game.GameMap;
import shared.models.game.Hex;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents the location of a vertex on a hex map
 */
public class VertexLocation
{
	private HexLocation hexLoc;
	private VertexDirection dir;
	
	public VertexLocation(HexLocation hexLoc, VertexDirection dir)
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
	
	public VertexDirection getDir()
	{
		return dir;
	}
	
	private void setDir(VertexDirection direction)
	{
		this.dir = direction;
	}
	
	@Override
	public String toString()
	{
		return "VertexLocation [hexLoc=" + hexLoc + ", dir=" + dir + "]";
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
		VertexLocation other = (VertexLocation)obj;
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
	 * Returns a canonical (i.e., unique) value for this vertex location. Since
	 * each vertex has three different locations on a map, this method converts
	 * a vertex location to a single canonical form. This is useful for using
	 * vertex locations as map keys.
	 * 
	 * @return Normalized vertex location
	 */
	public VertexLocation getNormalizedLocation()
	{
		
		// Return location that has direction NW or NE
		
		switch (dir)
		{
			case NorthWest:
			case NorthEast:
				return this;
			case West:
				return new VertexLocation(
										  hexLoc.getNeighborLoc(EdgeDirection.SouthWest),
										  VertexDirection.NorthEast);
			case SouthWest:
				return new VertexLocation(
										  hexLoc.getNeighborLoc(EdgeDirection.South),
										  VertexDirection.NorthWest);
			case SouthEast:
				return new VertexLocation(
										  hexLoc.getNeighborLoc(EdgeDirection.South),
										  VertexDirection.NorthEast);
			case East:
				return new VertexLocation(
										  hexLoc.getNeighborLoc(EdgeDirection.SouthEast),
										  VertexDirection.NorthWest);
			default:
				assert false;
				return null;
		}
	}
    public Set<EdgeLocation> getEdges(){
        HashSet<EdgeLocation> edges = new HashSet<>();
        HexLocation neighbor;
        switch (getDir()){
            case NorthEast:
                edges.add(new EdgeLocation(getHexLoc(), EdgeDirection.North).getNormalizedLocation());
                edges.add(new EdgeLocation(getHexLoc(),EdgeDirection.NorthEast).getNormalizedLocation());
                neighbor = getHexLoc().getNeighborLoc(EdgeDirection.North);
                edges.add(new EdgeLocation(neighbor, EdgeDirection.SouthEast).getNormalizedLocation());
                break;
            case East:
                edges.add(new EdgeLocation(getHexLoc(), EdgeDirection.NorthEast).getNormalizedLocation());
                edges.add(new EdgeLocation(getHexLoc(),EdgeDirection.SouthEast).getNormalizedLocation());
                neighbor = getHexLoc().getNeighborLoc(EdgeDirection.NorthEast);
                edges.add(new EdgeLocation(neighbor,EdgeDirection.South).getNormalizedLocation());
                break;
            case SouthEast:
                edges.add(new EdgeLocation(getHexLoc(), EdgeDirection.SouthEast).getNormalizedLocation());
                edges.add(new EdgeLocation(getHexLoc(),EdgeDirection.South).getNormalizedLocation());
                neighbor = getHexLoc().getNeighborLoc(EdgeDirection.SouthEast);
                edges.add(new EdgeLocation(neighbor,EdgeDirection.SouthWest).getNormalizedLocation());
                break;
            case SouthWest:
                edges.add(new EdgeLocation(getHexLoc(), EdgeDirection.South).getNormalizedLocation());
                edges.add(new EdgeLocation(getHexLoc(),EdgeDirection.SouthWest).getNormalizedLocation());
                neighbor = getHexLoc().getNeighborLoc(EdgeDirection.South);
                edges.add(new EdgeLocation(neighbor,EdgeDirection.NorthWest).getNormalizedLocation());
                break;
            case West:
                edges.add(new EdgeLocation(getHexLoc(), EdgeDirection.SouthWest).getNormalizedLocation());
                edges.add(new EdgeLocation(getHexLoc(),EdgeDirection.NorthWest).getNormalizedLocation());
                neighbor = getHexLoc().getNeighborLoc(EdgeDirection.SouthWest);
                edges.add(new EdgeLocation(neighbor,EdgeDirection.North).getNormalizedLocation());
                break;
            case NorthWest:
                edges.add(new EdgeLocation(getHexLoc(), EdgeDirection.NorthWest).getNormalizedLocation());
                edges.add(new EdgeLocation(getHexLoc(),EdgeDirection.North).getNormalizedLocation());
                neighbor = getHexLoc().getNeighborLoc(EdgeDirection.NorthWest);
                edges.add(new EdgeLocation(neighbor,EdgeDirection.NorthEast).getNormalizedLocation());
                break;
        }
        return edges;
    }
}

