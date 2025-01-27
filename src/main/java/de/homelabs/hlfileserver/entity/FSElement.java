package de.homelabs.hlfileserver.entity;

public class FSElement {
	private String name			= "";
	private long created		= 0;
	private long modified		= 0;
	private boolean global		= false;
	private boolean directory	= false;
	private int uid				= 0;
	private int gid				= 0;
	
	public FSElement(String name, long created, long modified, boolean global, boolean directory, int uid, int gid) {
		super();
		this.name = name;
		this.created = created;
		this.modified = modified;
		this.global = global;
		this.directory = directory;
		this.uid = uid;
		this.gid = gid;
	}
	
	public String getName()			{		return this.name;		}	
	public long getCreated()		{		return this.created; 	}
	public long getModified()		{		return this.modified;	}
	public boolean isGlobal()		{		return this.global;		}
	public boolean isDirectory()	{		return this.directory;	}
	public int getUID() 			{		return this.uid;		}
	public int getGID() 			{		return this.gid;		}
}
