package de.homelabs.hlfileserver.entity;

/**
 * basePath - path to files
 * maxFileSize - max file size to read
 */
public record FileserverProperties(
		String basePath, int maxFileSize) {
}
