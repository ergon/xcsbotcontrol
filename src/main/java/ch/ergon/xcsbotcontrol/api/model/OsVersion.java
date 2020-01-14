package ch.ergon.xcsbotcontrol.api.model;

public class OsVersion implements Comparable<OsVersion> {
	protected int major;
	protected int minor;
	protected int revision;

	public OsVersion(int major, int minor, int revision) {
		this.major = major;
		this.minor = minor;
		this.revision = revision;
	}

	@Override
	public int compareTo(OsVersion other) {
		if (Integer.compare(major, other.major) == 0) {
			if (Integer.compare(minor, other.minor) == 0) {
				return Integer.compare(revision, other.revision);
			} else {
				return Integer.compare(minor, other.minor);
			}
		} else {
			return Integer.compare(major, other.major);
		}
	}
}
