# ePassport Authenticated Discovery

Research materials for **Implementation Variation and Authenticated Discovery in ePassport Verification** by James F. Shields, Emmanuel Nuakoh, Joseph Antonucci, and Farjana Akter.

This project examines implementation variation in electronic passports and two experimental approaches to authenticating discovery information: **Modified Doc 9303** and an early prototype of the **Modern Identity Document Security Standard (MIDSS)**.

## Study overview

An ePassport verifier may use EF.COM to discover available data groups. EF.COM is unsigned, while EF.SOD authenticates data-group hash references. If a verifier relies on EF.COM without reconciling it against authenticated state, removing the DG15 advertisement can affect whether Active Authentication is attempted.

The study combines descriptive records from 16 holder-authorized eMRTD observations with two synthetic experiments. Each experiment places discovery information within the signed byte stream, verifies an unchanged baseline, and tests a controlled post-signing omission while retaining the original signature.

The reported results demonstrate detection of the tested changes. They do not establish production readiness, interoperability, operational effectiveness, or superiority of either design.

## Materials and release status

The research materials are organized into three groups:

| Group | Contents |
| --- | --- |
| Experimental evidence | Original and modified synthetic files for both frameworks, original signatures, verification public keys, mutation records, and signing and verification logs. |
| Reproducibility records | Corpus observations and descriptions of the experimental file structures and signature arrangements. |
| Supporting code | Utilities for copying raw bytes, reading DG2 facial-image data, and constructing a face-information object with placeholder metadata. |

**Package status:** This README was prepared from the manuscript, two spreadsheets, and four supporting source files. The synthetic before-and-after datasets, experimental logs, and signing, mutation, and verification scripts were not supplied with those attachments. Their inclusion and execution must be checked before this release is described as a complete executable reproduction package.

### Spreadsheets

- `Corpus_Implementation_Data(4).xlsx`: Records the 16 observations, acquisition tools, observed data groups and elementary files, and available size and transfer measurements. An unlisted element was not observed; this does not prove it was absent. Data-group counts exclude EF.COM, EF.SOD, and EF.CardAccess.
- `Experimental_Frameworks(4).xlsx`: Describes the data files and signature arrangements used by the two experimental frameworks. It is a structural summary, not the underlying experimental dataset or a verification log.

These names identify the supplied copies; update them here if files are renamed for publication.

## Experimental frameworks

| Property | Modified Doc 9303 | MIDSS prototype |
| --- | --- | --- |
| Signed byte sequence | DG1, DG2, DG15, EF.COM, in that order | DG1, DG2, DG3, DG5, in that order |
| Signature | Detached RSA-4096/SHA-256 signature in an EF.SOD-named file | Detached RSA-3072/SHA-256 signature |
| Controlled modification | Remove the DG15 identifier (`0x6F`) from EF.COM | Remove the DG5 index entry (`0x05`) from DG1 |
| Reported baseline result | Signature verification succeeds | Signature verification succeeds |
| Reported altered-state result | Signature verification fails | Signature verification fails |

Modified Doc 9303 uses a synthetic detached signature, **not a standards-conformant EF.SOD object**. Its tested omission did not update EF.COM TLV lengths, leaving malformed EF.COM. The result therefore tests signature integrity, not acceptance of a structurally valid modified EF.COM.

The MIDSS experiment concatenates raw file bytes without length prefixes and uses a fixed verification file list. It does not test index-driven discovery. This experimental prototype should be distinguished from the later MIDSS v1.0 specification.

### Reproduction procedure

For each framework, the complete experimental release should provide the inputs and commands needed to:

1. Verify the archived baseline using its original signature and corresponding public key.
2. Apply the documented omission to a separate copy of the baseline files.
3. Keep the original signature and all non-target files unchanged.
4. Verify the altered files and record the failed signature-verification result.
5. Compare the outputs with the archived logs.

Document exact filenames, file order, mutation location, dependency versions, commands, and expected outputs alongside the experimental files. Do not regenerate the signature after modifying the signed data. A newly generated run should be identified separately from the archived run reported in the paper.

## Supporting scripts

| Source file | Function and limitations |
| --- | --- |
| `dat_convert.py` | Copies input bytes unchanged to a `.dat` file and reports the bytes read and written. It does not convert the underlying format or construct a DG2 structure. |
| `DatToImageConverter.java` | Uses JMRTD to read the first facial image from a DG2 file and attempts to save it as JPEG. The supplied version has a hardcoded output path. |
| `EPassportDataParser.java` | Reads DG2 facial-image metadata and attempts image extraction. The supplied version has a hardcoded input path and passes an encoded face-image record to ImageIO; image extraction requires review before relying on it. |
| `FaceInfoExtractor.java` | Constructs a JMRTD face-information object using an image and placeholder metadata. It does not perform facial-feature detection or inference. |

The uploaded source copies include numbered filename suffixes. Restore Java filenames to their public class names before compilation and place them under the `passport` package. The Python upload is named `dat_convert(10).py`; this README uses `dat_convert.py` as its repository filename.

### Python utility

Requires Python 3 and uses only the standard library. After saving the script as `dat_convert.py`, run:

```bash
python3 dat_convert.py "path/to/input_file"
```

The script prompts for an output file or folder. Running it without an argument also prompts for the input path. An existing output file will be overwritten.

### Java utilities

The sources depend on JMRTD, Scuba classes, and Java ImageIO. `FaceInfoExtractor.java` uses `List.of`, requiring Java 9 or later. Exact compatible library versions and any image-codec dependencies must be recorded in the build configuration. Remove hardcoded personal paths and passport-derived filenames before publication.

These utilities are supporting research code. They do not implement the signing, mutation, or verification workflows for the two experiments, and this README does not certify that they compile or execute successfully.

## Data handling and scope

Physical passport reads were holder-authorized and read-only. Controlled modifications were applied only to synthetic experimental files.

Raw passport-derived LDS files, facial images, and unredacted identity-document records are restricted and are not intended for public distribution. Public experimental fixtures should contain synthetic data. Logs and source comments should be checked for identifying information before release.

The corpus is a purposive sample of 16 observations, not a representative estimate of issuer practice. Nine observations had retained native elementary-file exports; the remaining observations supported corpus characterization without that retained file-level evidence. No operational border inspection system was evaluated.

## Citation and contact

When using these materials, cite the accompanying manuscript:

Shields, J. F., Nuakoh, E., Antonucci, J., and Akter, F. *Implementation Variation and Authenticated Discovery in ePassport Verification*. Research manuscript.

Update this citation with the published article details and the archived repository release identifier when available. No journal acceptance or publication is implied.

Corresponding author: James F. Shields, jfs62@njit.edu.

## License

A reuse license has not been specified in the supplied materials. Consult the repository's `LICENSE` file when one is added; public availability alone does not grant a software or data license.
