package ch.ergon.xcsbotcontrol.api.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import com.google.common.base.Strings;

public class SourceControlBlueprintBuilder {
	private final SourceControlBlueprint blueprint = new SourceControlBlueprint();
	private List<RepositoryBuilder> repositorySiteBuilders = new ArrayList<>();

	public static SourceControlBlueprintBuilder blueprint() {
		return new SourceControlBlueprintBuilder();
	}

	public SourceControlBlueprint build() {
		for (RepositoryBuilder siteBuilder : repositorySiteBuilders) {
			RepositorySite site = siteBuilder.build();
			String repoId = site.repository.repoId;
			blueprint.remoteRepository.add(site.repository);
			blueprint.authentication.put(repoId, site.authentication);
			blueprint.branch.put(repoId, site.branch);
			blueprint.workingCopyPath.put(repoId, site.workingCopyPath);
			blueprint.workingCopyStates.put(repoId, BigDecimal.ZERO); // yet another magic value
			if (site.primary) {
				blueprint.primaryRepoId = repoId;
			}
		}
		// Default to first repo if no primary defined
		if (Strings.isNullOrEmpty(blueprint.primaryRepoId)) {
			if (blueprint.remoteRepository.size() > 0) {
				blueprint.primaryRepoId = blueprint.remoteRepository.get(0).repoId;
			}
		}

		return blueprint;
	}

	public SourceControlBlueprintBuilder name(String name) {
		blueprint.blueprintName = name;
		return this;
	}

	public SourceControlBlueprintBuilder mainRepositoryRelativePathToProject(String relativePath) {
		blueprint.relativePathToProject = relativePath;
		return this;
	}

	public SourceControlBlueprintBuilder addRepository(RepositoryBuilder repository) {
		repositorySiteBuilders.add(repository);
		return this;
	}

	public static class RepositoryBuilder {
		// Might be cleaner to use sub-builders also in site, but don't see the advantage currently
		private final RepositorySite site = new RepositorySite();

		public static RepositoryBuilder repository() {
			return new RepositoryBuilder();
		}

		public RepositorySite build() {
			return site;
		}

		public RepositoryBuilder primary() {
			site.primary = true;
			return this;
		}

		public RepositoryBuilder remoteUrl(String url) {
			site.repository.remoteUrl = url;
			return this;
		}

		public RepositoryBuilder workingCopyPath(String relativePath) {
			site.workingCopyPath = relativePath;
			return this;
		}

		public RepositoryBuilder certificateFingerprint(String fingerprint) {
			site.repository.certificateFingerprint = fingerprint;
			return this;
		}

		public RepositoryBuilder withAuthentication(AuthenticationBuilder authentication) {
			site.authentication = authentication.build();
			return this;
		}

		public RepositoryBuilder branch(String branch) {
			site.branch.branch = branch;
			return this;
		}
	}

	static class RepositorySite  {
		protected SourceControlBlueprint.RemoteRepository repository = new SourceControlBlueprint.RemoteRepository();
		protected SourceControlBlueprint.Authentication authentication = new SourceControlBlueprint.Authentication();
		protected SourceControlBlueprint.Branch branch = new SourceControlBlueprint.Branch();
		protected String workingCopyPath;
		protected boolean primary = false;
	}

	public static class AuthenticationBuilder {
		private final SourceControlBlueprint.Authentication auth = new SourceControlBlueprint.Authentication();

		public static AuthenticationBuilder authentication() {
			return new AuthenticationBuilder();
		}

		public SourceControlBlueprint.Authentication build() {
			return auth;
		}

		public AuthenticationBuilder sshKeyPasssphrase(String passphrase) {
			auth.sshKeyPassphrase = passphrase;
			return this;
		}

		public AuthenticationBuilder sshPrivateKey(String key) {
			auth.sshPrivateKey = new String(Base64.getEncoder().encode(key.getBytes()));
			return this;
		}

		public AuthenticationBuilder sshPublicKey(String key) {
			auth.sshPublicKey = new String(Base64.getEncoder().encode(key.getBytes()));
			return this;
		}
	}
}
