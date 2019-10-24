This is 99% for me as I develop

If something gets borked, blow it up, setupDecompWorkspace, the 'refresh' the gradle project (reimport) to fix the dependencies.  Wish I knew how it worked but for now at least I know *what* works.

mcmod.info just doesn't work in the dev environment, this is normal.

To export, use the gradle command `build`, NOT `jar` - `build` has the reobf hooks or something, I've just gotta trust the forge team at some point

[Here's the tuts I've been using](https://suppergerrie2.com/category/forge-tutorial/forge-tutorial-1-12/page/2/)