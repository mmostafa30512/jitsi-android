/*
 * Jitsi, the OpenSource Java VoIP and Instant Messaging client.
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jitsi.impl.androidversion;

import android.content.*;
import android.content.pm.*;

import net.java.sip.communicator.util.*;

import org.jitsi.android.*;
import org.jitsi.service.version.*;
import org.jitsi.service.version.util.*;

/**
 * Android version service implementation. Current version is parsed from
 * android:versionName attribute from AndroidManifest.xml.
 *
 * @author Pawel Domas
 */
public class VersionServiceImpl
    extends AbstractVersionService
{
    /**
     * The logger
     */
    private final static Logger logger
        = Logger.getLogger(VersionServiceImpl.class);

    /**
     * Current version instance.
     */
    private final VersionImpl CURRENT_VERSION;

    /**
     * Creates new instance of <tt>VersionServiceImpl</tt> and parses current
     * version from android:versionName attribute from AndroidManifest.xml.
     */
    public VersionServiceImpl()
    {
        Context ctx = JitsiApplication.getGlobalContext();
        PackageManager pckgMan = ctx.getPackageManager();
        try
        {
            PackageInfo pckgInfo
                = pckgMan.getPackageInfo(ctx.getPackageName(), 0);

            int versionCode = pckgInfo.versionCode;
            String versionName = pckgInfo.versionName;

            CURRENT_VERSION
                = (VersionImpl) parseVersionString(versionName);

            logger.info(
                "Jitsi version: " + CURRENT_VERSION
                    + ", version code: " + versionCode);
        }
        catch (PackageManager.NameNotFoundException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns a <tt>Version</tt> object containing version details of the
     * Jitsi version that we're currently running.
     *
     * @return a <tt>Version</tt> object containing version details of the
     *   Jitsi version that we're currently running.
     */
    public Version getCurrentVersion()
    {
        return CURRENT_VERSION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Version createVersionImpl(int majorVersion,
                                        int minorVersion,
                                        String nightlyBuildId)
    {
        return new VersionImpl(majorVersion, minorVersion, nightlyBuildId);
    }
}
