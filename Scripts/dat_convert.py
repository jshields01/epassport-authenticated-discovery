# Copies an input file's raw bytes to a .dat file without changing their contents.
# Accepts an input path and prompts for an output file or folder, then reports
# the number of bytes read and written. Does not perform format conversion
# or construct an ePassport DG2 structure.

import os
import sys

def clean_path(p: str) -> str:
    p = p.strip()
    # Accept pasted paths with surrounding quotes from "Copy as path"
    if len(p) >= 2 and ((p[0] == '"' and p[-1] == '"') or (p[0] == "'" and p[-1] == "'")):
        p = p[1:-1].strip()
    return p

def prompt(label: str) -> str:
    try:
        return clean_path(input(label))
    except KeyboardInterrupt:
        print("\nCancelled.")
        sys.exit(1)

def main():
    print("DAT Converter (raw bytes → .dat)")
    print("Paste paths exactly as copied (quotes are OK).")
    print("Tip: For output, you can paste a folder OR a full .dat file path.")
    print()

    # Input path: argument or prompt
    if len(sys.argv) >= 2:
        in_path = clean_path(sys.argv[1])
    else:
        in_path = prompt("Input file path: ")

    if not os.path.isfile(in_path):
        print(f"\nInput file not found:\n  {in_path}")
        sys.exit(1)

    out_user = prompt("Output path (folder or file): ")

    # If user pasted a folder, write inside it using input filename with .dat
    # Heuristic: if it exists and is a directory OR endswith slash/backslash
    if (os.path.exists(out_user) and os.path.isdir(out_user)) or out_user.endswith(("\\", "/")):
        out_dir = out_user.rstrip("\\/")

        # Create folder if it doesn't exist
        os.makedirs(out_dir, exist_ok=True)

        base = os.path.splitext(os.path.basename(in_path))[0]
        out_path = os.path.join(out_dir, base + ".dat")
    else:
        # Treat as file path
        out_path = out_user
        if not out_path.lower().endswith(".dat"):
            out_path += ".dat"

        out_dir = os.path.dirname(out_path)
        if out_dir:
            os.makedirs(out_dir, exist_ok=True)

    # Copy raw bytes
    with open(in_path, "rb") as f:
        data = f.read()

    with open(out_path, "wb") as f:
        f.write(data)
        f.flush()
        os.fsync(f.fileno())

    # Confirm actual file size on disk
    actual = os.path.getsize(out_path)

    print("\nConverted:")
    print(f"  IN : {in_path}")
    print(f"  OUT: {out_path}")
    print(f"Bytes read   : {len(data)}")
    print(f"Bytes on disk: {actual}")

if __name__ == "__main__":
    main()

