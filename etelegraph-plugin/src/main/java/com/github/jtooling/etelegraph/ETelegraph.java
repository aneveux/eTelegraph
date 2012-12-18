/*
 * eTelegraph - An Eclipse plugin which allows to display user-friendly
 * notifications inside Eclipse...
 *   Copyright (c) 2012, Antoine Neveux
 *   All rights reserved.
 *
 *   Redistribution and use in source and binary forms, with or without
 *   modification, are permitted provided that the following conditions
 *   are met:
 *
 *   1. Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 *   2. Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 *   3. Neither the name of the project's author nor the names of its
 *   contributors may be used to endorse or promote products derived from
 *   this software without specific prior written permission.
 *
 *   THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 *   "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 *   LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 *   FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 *   COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 *   INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 *   BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *   LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 *   CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 *   LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 *   WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 *   POSSIBILITY OF SUCH DAMAGE.
 */
package com.github.jtooling.etelegraph;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.github.jtooling.jtelegraph.Telegraph;
import com.github.jtooling.jtelegraph.TelegraphQueue;

/**
 * The activator class controls the plug-in life cycle
 * 
 * @author Antoine Neveux
 * @version 1.0
 */
public class ETelegraph extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.github.jtooling.etelegraph"; //$NON-NLS-1$

	// The shared instance
	private static ETelegraph plugin;

	/**
	 * The {@link TelegraphQueue} instance to be used by all the plugins
	 */
	private final TelegraphQueue telegraphQueue;

	/**
	 * The constructor
	 */
	public ETelegraph() {
		telegraphQueue = new TelegraphQueue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext
	 * )
	 */
	@Override
	public void start(final BundleContext context) throws Exception {
		super.start(context);
		ETelegraph.plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext
	 * )
	 */
	@Override
	public void stop(final BundleContext context) throws Exception {
		telegraphQueue.join();
		ETelegraph.plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static ETelegraph getDefault() {
		return ETelegraph.plugin;
	}

	/**
	 * Allows to push a {@link Telegraph} to the global notification center. All
	 * {@link Telegraph} are centralized, so yours will be put in a queue, and
	 * processed as soon as there isn't any older in the queue.
	 * 
	 * @see Telegraph
	 * @param telegraph
	 *            the {@link Telegraph} object you'd like to show in Eclipse
	 */
	public static void push(final Telegraph telegraph) {
		ETelegraph.getDefault().telegraphQueue.add(telegraph);
	}

}
